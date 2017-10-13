package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageHuikuanDTO;
import com.yimei.hs.same.entity.Fukuan;
import com.yimei.hs.same.entity.Huikuan;
import com.yimei.hs.same.entity.HuikuanMap;
import com.yimei.hs.same.mapper.FukuanMapper;
import com.yimei.hs.same.mapper.HuikuanMapMapper;
import com.yimei.hs.same.mapper.HuikuanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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
    private FukuanMapper fukuanMapper;

    @Autowired
    private HuikuanMapper huikuanMapper;

    @Autowired
    private LogService logService;

    /**
     *
     * @param pageHuikuanDTO
     * @return
     */
    public Page<Huikuan> getPage(PageHuikuanDTO pageHuikuanDTO) {

        // 获取回款列表
        Page<Huikuan> huikuanPage=huikuanMapper.getPage(pageHuikuanDTO);

        // 对每一笔回款， 获取对应的付款列表
        for (Huikuan huikuan:huikuanPage.getResults()) {
            List<Fukuan> fukuans = huikuanMapper.getFukuanListByHuikuanId(huikuan.getId());
            huikuan.setFukuanList(fukuans);
        }
        return huikuanPage;
    }

    public Huikuan findOne(long id) {
        return huikuanMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public int update(Huikuan huikuan) {
        return huikuanMapper.updateByPrimaryKeySelective(huikuan);
    }

    /**
     * 创建回款
     * @param huikuan
     * @return
     */
    @Transactional(readOnly = false)
    public int create(Huikuan huikuan) {
        // 1. 插入回款记录
        int rtn = huikuanMapper.insert(huikuan);

        // 2. 触发建立 回款-还款映射
        this.createMapping(huikuan.getOrderId());

        return rtn;
    }

    /**
     * 逻辑删除 由于回款时自动对应到付款的, 删除回款记录, 需要重建整个业务线的 回款-付款-map记录
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(long orderId, long id) {

        // 1. 删除所有的回款对应明细
        huikuanMapMapper.deleteByOrderId(orderId);

        // 2. 删除回款
        int rtn = huikuanMapper.delete(id);

        // 3. 重新
        this.createMapping(orderId);

        return rtn;
    }

    /**
     * 为某个订单重建 回款-付款 对应关系
     * @param orderId
     */
    public void createMapping(Long orderId) {

        // 1. 找出付款尚未完成回款对应的付款记录
        List<Fukuan> unfinishedFukuan = fukuanService.huikuanUnfinished(orderId);

        // 2. 如果所有付款都已经回款完
        if (unfinishedFukuan.size() == 0) {
            return;
        }

        // 3. 待创建的对应记录
        List<HuikuanMap> toAdd = new ArrayList<>();

        // 1.  找出订单的回款记录 - 尚有未对应完的余额,  也就是回款还有余额
        List<Huikuan> unfinished = huikuanMapper.getUnfinshedByOrderId(orderId);
        Iterator<Fukuan> it = unfinishedFukuan.iterator();
        for (Huikuan huikuan : unfinished) {

            // 尚未对应完的余额
            BigDecimal total = huikuan.getHuikuanAmount().subtract(huikuan.getFukuanTotal());

            while(it.hasNext()) {

                Fukuan cur = it.next();

                HuikuanMap record = new HuikuanMap();
                record.setOrderId(huikuan.getOrderId());
                record.setHuikuanId(huikuan.getId());
                record.setFukuanId(cur.getId());

                BigDecimal toFinished = cur.getPayAmount().subtract(cur.getHuikuanTotal());

                if(total.compareTo(toFinished) != -1) {
                    total = total.subtract(toFinished);
                    record.setAmount(toFinished);
                    toAdd.add(record);
                } else {
                    record.setAmount(total);
                    toAdd.add(record);
                    break;
                }
            }
        }

        for (HuikuanMap record : toAdd) {
            huikuanMapMapper.insert(record);
        }
    }
}



