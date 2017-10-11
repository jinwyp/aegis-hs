package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.*;
import com.yimei.hs.ying.dto.PageYingFukuanDTO;
import com.yimei.hs.ying.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
@Transactional(readOnly = true)
public class YingFukuanService {

    private static final Logger logger = LoggerFactory.getLogger(YingFukuanService.class);

    @Autowired
    private YingFukuanMapper yingFukuanMapper;

    @Autowired
    private YingHuikuanMapMapper yingHuikuanMapMapper;

    @Autowired
    private YingHuikuanMapper yingHuikuanMapper;

    @Autowired
    private YingHuankuanMapper yingHuankuanMapper;

    @Autowired
    private YingHuankuanMapMapper yingHuankuanMapMapper;

    @Autowired
    private YingHuikuanService yingHuikuanService;

    @Autowired
    private YingHuankuanService yingHuankuanService;

    @Autowired
    private YingLogService yingLogService;

    /**
     * 获取一页付款记录
     *
     * @param pageYingFukuanDTO
     * @return
     */
    public Page<YingFukuan> getPage(PageYingFukuanDTO pageYingFukuanDTO) {

        // 1. 去除付款列表
        Page<YingFukuan> page = yingFukuanMapper.getPage(pageYingFukuanDTO);

        // 2. 对每一笔付款，
        // 关联: 回款列表
        //       回款map明细
        //      还款列表
        //      还款map明细
        for (YingFukuan yingFukuan : page.getResults()) {
            // 关联回款列表
            List<YingHuikuan> huikuanList = yingHuikuanMapper.getListByFukuanID(yingFukuan.getId());
            yingFukuan.setHuikuanList(huikuanList);

            // 关联回款map明细
            List<YingHuikuanMap> huikuanMap = yingHuikuanMapMapper.getListByFukuanId(yingFukuan.getId());
            yingFukuan.setHuikuanMap(huikuanMap);

            // 关联还款列表
            List<YingHuankuan> huankuanList = yingHuankuanMapper.getListByFukuanId(yingFukuan.getId());
            yingFukuan.setHuankuanList(huankuanList);

            // 关联还款map明细
            List<YingHuankuanMap> huankuanMap = yingHuankuanMapMapper.getListByFukuanId(yingFukuan.getId());
            yingFukuan.setHuankuanMap(huankuanMap);
        }

        if (
                pageYingFukuanDTO.getHuikuanUnfinished() != null
                        && pageYingFukuanDTO.getHuikuanUnfinished()
                ) {
            page.setResults(this.getHuikuanUnifished(page.getResults()));
        }

        if (pageYingFukuanDTO.getHuankuanUnfinished() != null
                && pageYingFukuanDTO.getHuankuanUnfinished()
                ) {

            page.setResults(this.getHuankuanUnfinished(page.getResults()));
        }
        return page;
    }

    /**
     * 找出当前订单的付款列表: (条件为: 回款尚未回完的付款)
     *
     * @param orderId
     * @return
     */
    public List<YingFukuan> huikuanUnfinished(long orderId) {
        PageYingFukuanDTO dto = new PageYingFukuanDTO();
        dto.setPageSize(1000000000);
        dto.setPageNo(1);
        dto.setOrderId(orderId);

        Page<YingFukuan> page = yingFukuanMapper.getPage(dto);
        for (YingFukuan yingFukuan : page.getResults()) {
            List<YingHuikuanMap> huikuanMap = yingHuikuanMapMapper.getListByFukuanId(yingFukuan.getId());
            yingFukuan.setHuikuanMap(huikuanMap);
        }
        return this.getHuikuanUnifished(page.getResults());
    }

    /**
     * 找出当前订单所有的付款  (条件为: 付款未被还完)
     *
     * @param orderId
     * @return
     */
    public List<YingFukuan> huankuanUnfinished(long orderId) {

        PageYingFukuanDTO dto = new PageYingFukuanDTO();
        dto.setPageSize(1000000000);
        dto.setPageNo(1);
        dto.setOrderId(orderId);

        Page<YingFukuan> page = yingFukuanMapper.getPage(dto);
        for (YingFukuan yingFukuan : page.getResults()) {

            List<YingHuankuanMap> huankuanMap = yingHuankuanMapMapper.getListByFukuanId(yingFukuan.getId());
            yingFukuan.setHuankuanMap(huankuanMap);   // 付款被还的map明细

        }

        // 实施过滤
        return getHuankuanUnfinished(page.getResults());
    }


    /**
     * 过滤付款列表: 返回付款没有被回款完的付款列表
     *
     * @param fukuans
     * @return
     */
    private List<YingFukuan> getHuikuanUnifished(List<YingFukuan> fukuans) {
        List<YingFukuan> filter = new ArrayList<>();
        for (YingFukuan fukuan : fukuans) {

            // 计算此付款被回部分的总额
            List<YingHuikuanMap> huikuanMap = fukuan.getHuikuanMap();
            BigDecimal total = huikuanMap.stream().map(m -> m.getAmount()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));

            fukuan.setHuikuanTotal(total);
            if (!total.equals(fukuan.getPayAmount())) {
                filter.add(fukuan);
            }
        }
        return filter;
    }


    /**
     * 过滤付款列表, 返回付款没有被还完的付款列表
     *
     * @param fukuans
     * @return
     */
    private List<YingFukuan> getHuankuanUnfinished(List<YingFukuan> fukuans) {

        List<YingFukuan> filter = new ArrayList<>();
        for (YingFukuan fukuan : fukuans) {

            // 计算此付款被换掉的总额
            List<YingHuankuanMap> huankuanMap = fukuan.getHuankuanMap();
            BigDecimal total = huankuanMap.stream().map(m -> m.getPrincipal()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));

            fukuan.setHuankuanTotal(total);
            if (!total.equals(fukuan.getPayAmount())) {
                filter.add(fukuan);
            }
        }

        return filter;
    }


    /**
     * 创建付款 - 触发 回款-付款-对应关系的建立
     *
     * @param yingFukuan
     * @return
     */
    @Transactional(readOnly = false)
    public int create(YingFukuan yingFukuan) {

        // 1. 插入付款记录
        int rtn = yingFukuanMapper.insert(yingFukuan);

        // 2. 触发回款对应
        yingFukuan.setHuikuanTotal(BigDecimal.ZERO);
        yingHuikuanService.createMapping(yingFukuan.getOrderId());

        return rtn;
    }

    public YingFukuan findOne(long id) {
        return yingFukuanMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public int update(YingFukuan yingFukuan) {
        return yingFukuanMapper.updateByPrimaryKeySelective(yingFukuan);
    }

    /**
     * 删除指定订单的付款记录， 这时候， 需要重建所有 回款-付款-map,  还款-付款-map
     *
     * @param orderId
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(Long orderId, long id) {
        yingHuikuanService.createMapping(orderId);

        // todo
        return yingFukuanMapper.delete(id);
    }
}
