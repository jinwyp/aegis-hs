package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageFukuanDTO;
import com.yimei.hs.same.entity.*;
import com.yimei.hs.same.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by hary on 2017/9/15.
 */
@Service
@Transactional(readOnly = true)
public class FukuanService {

    private static final Logger logger = LoggerFactory.getLogger(FukuanService.class);

    @Autowired
    private FukuanMapper fukuanMapper;

    @Autowired
    private HuikuanMapMapper huikuanMapMapper;

    @Autowired
    private HuikuanMapper huikuanMapper;

    @Autowired
    JiekuanMapper jiekuanMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    private HuikuanService huikuanService;

    /**
     * 获取一页付款记录
     *
     * @param pageFukuanDTO
     * @return
     */
    public Page<Fukuan> getPage(PageFukuanDTO pageFukuanDTO) {

        // 1. 去除付款列表
        Page<Fukuan> page = fukuanMapper.getPage(pageFukuanDTO);

        // 2. 对每一笔付款， 关联回款列表, 回款map明细
        for (Fukuan fukuan : page.getResults()) {
            // 关联回款列表
            List<Huikuan> huikuanList = huikuanMapper.getListByFukuanID(fukuan.getId());
            fukuan.setHuikuanList(huikuanList);

            // 关联回款map明细
            List<HuikuanMap> huikuanMap = huikuanMapMapper.getListByFukuanId(fukuan.getId());
            fukuan.setHuikuanMap(huikuanMap);
        }

        return page;
    }

    /**
     * 获取当前订单尚未完成回款的付款
     * @param orderId
     * @return
     */
    public List<Fukuan> getListUnfinished(long orderId) {
        List<Fukuan> fukuans = fukuanMapper.getunTradeDeficittlist((orderId));

        // 2. 对每一笔付款， 关联回款列表, 回款map明细
        for (Fukuan fukuan : fukuans) {
            // 关联回款列表
            List<Huikuan> huikuanList = huikuanMapper.getListByFukuanID(fukuan.getId());
            fukuan.setHuikuanList(huikuanList);

            // 关联回款map明细
            List<HuikuanMap> huikuanMap = huikuanMapMapper.getListByFukuanId(fukuan.getId());
            fukuan.setHuikuanMap(huikuanMap);
        }

        // 3. 如果只需要回款尚未完成的付款， 则过滤
        return this.getHuikuanUnifished(fukuans);

    }


    /**
     * 找出当前订单的付款列表: (条件为: 回款尚未回完的付款)
     *
     * @param orderId
     * @return
     */
    public List<Fukuan> huikuanUnfinished(long orderId) {

        // 1. 获取订单的所有付款
        List<Fukuan> all = fukuanMapper.getunTradeDeficittlist(orderId);

        for (Fukuan fukuan : all) {
            // 设置付款所关联的回款明细
            List<HuikuanMap> huikuanMap = huikuanMapMapper.getListByFukuanId(fukuan.getId());
            fukuan.setHuikuanMap(huikuanMap);
        }
        return this.getHuikuanUnifished(all);
    }

    /**
     * 获取订单的所有付款
     *
     * @param orderId
     * @return
     */
    private List<Fukuan> getAll(long orderId) {
        PageFukuanDTO dto = new PageFukuanDTO();
        dto.setPageSize(1000000000);
        dto.setPageNo(1);
        dto.setOrderId(orderId);
        Page<Fukuan> page = fukuanMapper.getPage(dto);
        return page.getResults();
    }

    /**
     * 过滤付款列表: 返回付款没有被回款完的付款列表
     *
     * @param fukuans
     * @return
     */
    private List<Fukuan> getHuikuanUnifished(List<Fukuan> fukuans) {
        List<Fukuan> filter = new ArrayList<>();
        for (Fukuan fukuan : fukuans) {
            // 计算此付款被回部分的总额
            List<HuikuanMap> huikuanMap = fukuan.getHuikuanMap();
            BigDecimal total = huikuanMap.stream().map(m -> m.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);

            fukuan.setHuikuanTotal(total);
            if (!total.equals(fukuan.getPayAmount())) {
                filter.add(fukuan);
            }
        }
        return filter;
    }

    /**
     * 创建付款 - 触发 回款-付款-对应关系的建立
     *
     * @param fukuan
     * @return
     */
    @Transactional(readOnly = false)
    public int create(Fukuan fukuan) {

        List<Fukuan> fukuanList = fukuanMapper.getListByOrderIdAndHsId(fukuan.getOrderId(),fukuan.getHsId());
        LocalDateTime createDateTime = fukuan.getPayDate();
        List<Fukuan> isNull = (fukuanList == null ? null : fukuanList.stream().filter(fukuans -> createDateTime.isBefore(fukuans.getPayDate())).collect(toList()));
        // 1. 插入付款记录
        int rtn = fukuanMapper.insert(fukuan);
        if (isNull != null && isNull.size() > 0) {
            // 1. 删除订单的所有 回款-付款 映射
            huikuanMapMapper.deleteByOrderId(fukuan.getOrderId());
        }


        // 2. 触发回款对应
        fukuan.setHuikuanTotal(BigDecimal.ZERO);

        // 3. 触发回款明细的创建
        huikuanService.createMapping(fukuan.getOrderId());

        // 4. 当资金方不为自有资金时 触发借款记录
        Order order = orderMapper.selectByPrimaryKey(fukuan.getOrderId());
        if (order.getMainAccounting() != fukuan.getCapitalId()) {

            fukuan.getJiekuan().setFukuanId(fukuan.getId());
            jiekuanMapper.insert(fukuan.getJiekuan());
        }
        return rtn;
    }

    public Fukuan findOne(long id) {
        return fukuanMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public int update(Fukuan fukuan) {
        return fukuanMapper.updateByPrimaryKeySelective(fukuan);
    }

    /**
     * 删除指定订单的付款记录， 这时候， 需要重建所有 回款-付款-map,  还款-付款-map
     *
     * @param orderId
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete( long id,Long orderId) {

        // 1. 删除订单的所有 回款-付款 映射
        huikuanMapMapper.deleteByOrderId(orderId);

        // 2. 删除付款
        int rtn = fukuanMapper.delete(id);

        // 3. 重建回款付款映射
        huikuanService.createMapping(orderId);

        return rtn;
    }

}


