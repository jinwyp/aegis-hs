package com.yimei.hs.ying.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.*;
import com.yimei.hs.ying.dto.PageYingFukuanDTO;
import com.yimei.hs.ying.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
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
        Page<YingFukuan> page = yingFukuanMapper.getPage(pageYingFukuanDTO);
        for (YingFukuan yingFukuan : page.getResults()) {

            List<YingHuikuan> huikuanList = yingHuikuanMapper.getListByFukuanID(yingFukuan.getId());
            List<YingHuikuanMap> huikuanMap = yingHuikuanMapMapper.getListByFukuanId(yingFukuan.getId());
            yingFukuan.setHuikuanList(huikuanList);
            yingFukuan.setHuikuanMap(huikuanMap);

            List<YingHuankuan> huankuanList = yingHuankuanMapper.getListByFukuanId(yingFukuan.getId());
            List<YingHuankuanMap> huankuanMap = yingHuankuanMapMapper.getListByFukuanId(yingFukuan.getId());
            yingFukuan.setHuankuanList(huankuanList);
            yingFukuan.setHuankuanMap(huankuanMap);
        }

        if (pageYingFukuanDTO.getHuikuanUnfinished()) {
            page.setResults(this.getHuikuanUnifished(page.getResults()));
        }

        if (pageYingFukuanDTO.getHuankuanUnfinished()) {
            page.setResults(this.getHuankuanUnfinished(page.getResults()));
        }
        return page;
    }

    /**
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
            yingFukuan.setHuankuanMap(huankuanMap);
        }
        return getHuankuanUnfinished(page.getResults());
    }


    /**
     *
     * @param fukuans
     * @return
     */
    private List<YingFukuan> getHuikuanUnifished(List<YingFukuan> fukuans) {
        List<YingFukuan> filter = new ArrayList<>();
        for (YingFukuan fukuan : fukuans) {
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
     *
     * @param fukuans
     * @return
     */
    private List<YingFukuan> getHuankuanUnfinished(List<YingFukuan> fukuans) {
        List<YingFukuan> filter = new ArrayList<>();
        for (YingFukuan fukuan : fukuans) {
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
     * @param yingFukuan
     * @return
     */
    public int create(YingFukuan yingFukuan) {

        // 1. 插入付款记录
        int rtn = yingFukuanMapper.insert(yingFukuan);

        // 2. 触发回款对应
        yingHuikuanService.createMapping(yingFukuan.getOrderId());

        return rtn;
    }

    public YingFukuan findOne(long id) {
        return yingFukuanMapper.selectByPrimaryKey(id);
    }

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
    public int delete(Long orderId, long id) {
        yingHuikuanService.createMapping(orderId);

        // todo
        return yingFukuanMapper.delete(id);
    }
}
