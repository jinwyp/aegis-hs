package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageFukuanDTO;
import com.yimei.hs.same.entity.Fukuan;
import com.yimei.hs.same.entity.Huikuan;
import com.yimei.hs.same.entity.HuikuanMap;
import com.yimei.hs.same.entity.Jiekuan;
import com.yimei.hs.same.mapper.FukuanMapper;
import com.yimei.hs.same.mapper.HuikuanMapMapper;
import com.yimei.hs.same.mapper.HuikuanMapper;
import com.yimei.hs.same.mapper.JiekuanMapper;
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
    private HuikuanService huikuanService;

    @Autowired
    private LogService yingLogService;

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

            // 关联借款列表
            List<Jiekuan> jiekuanList = jiekuanMapper.getListByFukuanId(fukuan.getId());
            fukuan.setJiekuanList(jiekuanList);
        }

        // 如果只需要回款尚未完成的付款， 则过滤
        if ( pageFukuanDTO.getHuikuanUnfinished() != null && pageFukuanDTO.getHuikuanUnfinished() ) {
            page.setResults(this.getHuikuanUnifished(page.getResults()));
        }

        // 如果只需要借款尚未填充完毕的， 则过滤
        if (pageFukuanDTO.getJiekuanUnfinished() != null && pageFukuanDTO.getJiekuanUnfinished() ) {
            page.setResults(this.getJiekuanUnfinshed(page.getResults()));
        }

        return page;
    }

    /**
     * 找出当前订单的付款列表: (条件为: 回款尚未回完的付款)
     * @param orderId
     * @return
     */
    public List<Fukuan> huikuanUnfinished(long orderId) {

        List<Fukuan> all = this.getAll(orderId);
        for (Fukuan fukuan : all) {
            List<HuikuanMap> huikuanMap = huikuanMapMapper.getListByFukuanId(fukuan.getId());
            fukuan.setHuikuanMap(huikuanMap);
        }
        return this.getHuikuanUnifished(all);
    }

    /**
     * 获取订单的所有付款
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
     * 获取借款尚未添加完整的付款记录
     * @param orderId
     * @return
     */
    public List<Fukuan> jiekuanUnfinished(long orderId) {
        List<Fukuan> all = this.getAll(orderId);
        for (Fukuan fukuan : all) {
            List<Jiekuan> jiekuanList = jiekuanMapper.getListByFukuanId(fukuan.getId());
            fukuan.setJiekuanList(jiekuanList);
        }
        return this.getJiekuanUnfinshed(all);
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
    private List<Fukuan> getJiekuanUnfinshed(List<Fukuan> fukuans) {
        List<Fukuan> filter = new ArrayList<>();
        for (Fukuan fukuan : fukuans) {
            List<Jiekuan> jiekuanList = jiekuanMapper.getListByFukuanId(fukuan.getId());
            BigDecimal total = jiekuanList.stream()
                    .map(m -> m.getAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            fukuan.setJiekuanTotal(total);

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

        // 1. 插入付款记录
        int rtn = fukuanMapper.insert(fukuan);

        // 2. 触发回款对应
        fukuan.setHuikuanTotal(BigDecimal.ZERO);
        huikuanService.createMapping(fukuan.getOrderId());

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
    public int delete(Long orderId, long id) {
        huikuanService.createMapping(orderId);

        // todo
        return fukuanMapper.delete(id);
    }
}
