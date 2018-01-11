package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageHuikuanDTO;
import com.yimei.hs.same.entity.Fukuan;
import com.yimei.hs.same.entity.Huikuan;
import com.yimei.hs.same.entity.HuikuanMap;
import com.yimei.hs.same.mapper.FukuanMapper;
import com.yimei.hs.same.mapper.HuikuanMapMapper;
import com.yimei.hs.same.mapper.HuikuanMapper;
import com.yimei.hs.same.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by hary on 2017/9/15.
 */
@Service
@Transactional(readOnly = true)
public class HuikuanService {

    private static final Logger logger = LoggerFactory.getLogger(HuikuanService.class);

    @Autowired
    FukuanService fukuanService;

    @Autowired
    private HuikuanMapMapper huikuanMapMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private HuikuanMapper huikuanMapper;

    @Autowired
    private LogService logService;

    /**
     * @param pageHuikuanDTO
     * @return
     */
    public Page<Huikuan> getPage(PageHuikuanDTO pageHuikuanDTO) {

        // 获取回款列表
        Page<Huikuan> huikuanPage = huikuanMapper.getPage(pageHuikuanDTO);

        if (huikuanPage != null) {

            // 对每一笔回款， 获取对应的付款列表
            for (Huikuan huikuan : huikuanPage.getResults()) {
                List<Fukuan> fukuans = huikuanMapper.getFukuanListByHuikuanId(huikuan.getId());
                huikuan.setFukuanList(fukuans);
                huikuan.setHuikuanMapsList(huikuanMapMapper.getListByHuikuanId(huikuan.getId()));
            }
        }
        return huikuanPage;
    }

    public Huikuan findOne(long id) {
        return huikuanMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public int update(Huikuan huikuan) {

        List<Huikuan> huikuanList = huikuanMapper.gelistByhsIdAndOrderId(huikuan.getOrderId(), huikuan.getHsId());
        for (Huikuan huikuanDel:huikuanList) {
            huikuanMapMapper.deleteByHuikuanId(huikuanDel.getId());
        }
//        // todo 1. 删除回款明细
//        huikuanMapMapper.deleteByOrderId(huikuan.getOrderId(), huikuan.getHsId());

        // 2. 更新回款
        int rtn = huikuanMapper.updateByPrimaryKeySelective(huikuan);

        // 3. 重建回款明细
        this.createMapping(huikuan.getOrderId(), huikuan.getHsId());

        return rtn;
    }

    /**
     * 创建回款
     *
     * @param huikuan
     * @return
     */
    @Transactional(readOnly = false)
    public int create(Huikuan huikuan) {
        int rtn = 0;

        Long huikuanCompanyId = orderMapper.selectByPrimaryKey(huikuan.getOrderId()).getDownstreamId();
        huikuan.setHuikuanCompanyId(huikuanCompanyId);

        LocalDateTime currentTime = huikuan.getHuikuanDate();
        List<Huikuan> huikuanList = huikuanMapper.gelistByhsIdAndOrderId(huikuan.getOrderId(), huikuan.getHsId());

        List<Huikuan> isNull = (huikuanList == null ? null : huikuanList.stream().filter(h -> currentTime.isBefore(h.getHuikuanDate())).collect(toList()));

        if (isNull != null && isNull.size() > 0) {

            for (Huikuan huikuanDel:huikuanList) {
                huikuanMapMapper.deleteByHuikuanId(huikuanDel.getId());
            }

        }
        // 1. 插入回款记录
        rtn = huikuanMapper.insert(huikuan);
        // 2. 触发建立 回款-还款映射
        this.createMapping(huikuan.getOrderId(), huikuan.getHsId());


        return rtn;
    }

    /**
     * 逻辑删除 由于回款时自动对应到付款的, 删除回款记录, 需要重建整个业务线的 回款-付款-map记录
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(long id, long orderId) {
        Huikuan huikuan = huikuanMapper.selectByPrimaryKey(id);
//        // todo  1. 删除所有的回款对应明细
//        huikuanMapMapper.deleteByOrderId(orderId, huikuan.getHsId());


        List<Huikuan> huikuanList = huikuanMapper.gelistByhsIdAndOrderId(huikuan.getOrderId(), huikuan.getHsId());
        for (Huikuan huikuanDel:huikuanList) {
            huikuanMapMapper.deleteByHuikuanId(huikuanDel.getId());
        }
        // 2. 删除回款
        int rtn = huikuanMapper.delete(id);

        // 3. 重新
        this.createMapping(orderId, huikuan.getHsId());

        return rtn;
    }

    /**
     * 为某个订单重建 回款-付款 对应关系
     *
     * @param orderId
     */
    public void createMapping(Long orderId, Long hsId) {

        // 1. 找出付款尚未完成回款对应的付款记录
        List<Fukuan> unfinishedFukuan = fukuanService.huikuanUnfinished( hsId,orderId);

        // 2. 如果所有付款都已经回款完
        if (unfinishedFukuan.size() == 0) {
            return;
        }

        // 3. 待创建的对应记录
        List<HuikuanMap> toAdd = new ArrayList<>();

        // 1.  找出订单的回款记录 - 尚有未对应完的余额,  也就是回款还有余额
        List<Huikuan> unfinished = getALl(orderId,hsId);
//        huikuanMapper.getUnfinshedByOrderId(orderId);

//        Iterator<Fukuan> it = unfinishedFukuan.iterator();

        toAdd = toCompare(toAdd, unfinishedFukuan, unfinished);
//        Fukuan last = null;
//        BigDecimal lastValue = null;
//        for (Huikuan huikuan : unfinished) {
//
//            // 尚未对应完的余额
//            BigDecimal total = huikuan.getHuikuanAmount().subtract(huikuan.getFukuanTotal());
//
//            if (last != null) {
//                HuikuanMap record = new HuikuanMap();
//                record.setOrderId(huikuan.getOrderId());
//                record.setHuikuanId(huikuan.getId());
//                record.setFukuanId(last.getId());
//
//                BigDecimal toFinished = last.getPayAmount().subtract(last.getHuikuanTotal());
////
//                if (total.compareTo(toFinished) == 1) {
//                    total = total.subtract(toFinished);
//                    record.setAmount(toFinished);
//                    last = null;
//                    toAdd.add(record);
//                } else if(total.compareTo(toFinished) == 0){
//                    record.setAmount(toFinished);
//                    toAdd.add(record);
//                    last = null;
//                    continue;
//                } else {
//                    record.setAmount(total);
//                    toAdd.add(record);
//                    lastValue = toFinished.subtract(total);
//                    continue;
//                }
//            }
//
//            while (it.hasNext()) {
//
//                Fukuan cur = it.next();
//
//                HuikuanMap record = new HuikuanMap();
//                record.setOrderId(huikuan.getOrderId());
//                record.setHuikuanId(huikuan.getId());
//                record.setFukuanId(cur.getId());
//
//                BigDecimal toFinished = cur.getPayAmount().subtract(cur.getHuikuanTotal());
////
//                if (total.compareTo(toFinished) == 1) {
//                    total = total.subtract(toFinished);
//                    record.setAmount(toFinished);
//                    toAdd.add(record);
//                } else if(total.compareTo(toFinished) == 0){
//                    record.setAmount(toFinished);
//                    toAdd.add(record);
//                    break;
//                } else {
//                    record.setAmount(total);
//                    toAdd.add(record);
//                    last = cur;
//                    lastValue = toFinished.subtract(total);
//                    break;
//                }
//            }
//        }

        for (HuikuanMap record : toAdd) {
            huikuanMapMapper.insert(record);
        }
    }

    private List<HuikuanMap> toCompare(List<HuikuanMap> toAdd, List<Fukuan> unfinishedFukuan, List<Huikuan> unfinishedHuikuan) {
        Fukuan[] fukuans = unfinishedFukuan.toArray(new Fukuan[0]);
        Huikuan[] huikuans = unfinishedHuikuan.toArray(new Huikuan[0]);

        int fpos = -1;
        int hpos = -1;

        BigDecimal lastFukuan = new BigDecimal("0");
        BigDecimal lastHuikuan = new BigDecimal("0");
        while (hpos < huikuans.length && fpos < fukuans.length) {

            if (lastFukuan.compareTo(BigDecimal.ZERO) == 0) {
                fpos++;
                if (fpos == fukuans.length) {
                    break;
                }
                Fukuan tempFukuan = fukuans[fpos];
                lastFukuan = tempFukuan.getPayAmount().subtract(tempFukuan.getHuikuanTotal());

            }

            if (lastHuikuan.compareTo(BigDecimal.ZERO) == 0) {
                hpos++;
                if (hpos == huikuans.length) {
                    break;
                }
                Huikuan tempHuikuan = huikuans[hpos];
                lastHuikuan = tempHuikuan.getHuikuanAmount().subtract(tempHuikuan.getFukuanTotal());
            }

            HuikuanMap record = new HuikuanMap();
            record.setOrderId(huikuans[hpos].getOrderId());
            record.setHuikuanId(huikuans[hpos].getId());
            record.setFukuanId(fukuans[fpos].getId());


            if (lastHuikuan.compareTo(lastFukuan) == -1) {

                record.setAmount(lastHuikuan);
                toAdd.add(record);
                lastFukuan = lastFukuan.subtract(lastHuikuan);
                lastHuikuan = BigDecimal.ZERO;


            } else if (lastHuikuan.compareTo(lastFukuan) == 0) {
                record.setAmount(huikuans[hpos].getHuikuanAmount());
                toAdd.add(record);

                lastFukuan = BigDecimal.ZERO;
                lastHuikuan = BigDecimal.ZERO;
            } else {

                toAdd.add(record);
                lastHuikuan = lastHuikuan.subtract(lastFukuan);
                record.setAmount(lastFukuan);
                lastFukuan = BigDecimal.ZERO;
            }


        }


        return toAdd;
    }

//    找出订单的回款记录 - 尚有未对应完的余额,  也就是回款还有余额
    //1 找出所有回款记录

    public List<Huikuan> getALl(Long orderId,Long hsId) {

        PageHuikuanDTO dto = new PageHuikuanDTO();
        dto.setPageSize(100000000);
        dto.setPageNo(1);
        dto.setHsId(hsId);
        dto.setOrderId(orderId);
        List<Huikuan> list = this.getPage(dto).getResults();

        return this.getHuikuanUnifished(list);
    }
    //2 过滤已经对应完全的记录

    /**
     * 过滤回款列表: 返回付款没有被回款完的付款列表
     *
     * @param huikuans
     * @return
     */
    private List<Huikuan> getHuikuanUnifished(List<Huikuan> huikuans) {
        List<Huikuan> filter = new ArrayList<>();
        for (Huikuan huikuan : huikuans) {
            // 计算此回款被付款对应完总额
            List<HuikuanMap> huikuanMap = huikuan.getHuikuanMapsList();
            BigDecimal total = huikuanMap.stream().map(m -> m.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
            huikuan.setFukuanTotal(total);

            if (!total.equals(huikuan.getHuikuanAmount())) {
                filter.add(huikuan);
            }
        }
        return filter;
    }

}



