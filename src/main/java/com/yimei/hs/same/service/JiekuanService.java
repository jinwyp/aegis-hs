package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageJiekuanDTO;
import com.yimei.hs.same.entity.*;
import com.yimei.hs.same.entity.Jiekuan;
import com.yimei.hs.same.mapper.*;
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
public class JiekuanService {

    private static final Logger logger = LoggerFactory.getLogger(JiekuanService.class);

    @Autowired
    JiekuanMapper jiekuanMapper;

    @Autowired
    HuankuanMapMapper huankuanMapMapper;

    @Autowired
    HuankuanMapper huankuanMapper;

    /**
     * 获取一页借款记录
     * @param pageJiekuanDTO
     * @return
     */
    public Page<Jiekuan> getPage(PageJiekuanDTO pageJiekuanDTO) {
        Page<Jiekuan> page = jiekuanMapper.getPage(pageJiekuanDTO);
        return page;
    }

    /**
     * 创建借款
     * @param fukuan
     * @return
     */
    @Transactional(readOnly = false)
    public int create(Jiekuan fukuan) {
        int rtn = jiekuanMapper.insert(fukuan);
        return rtn;
    }

    /**
     * 查找借款
     * @param id
     * @return
     */
    public Jiekuan findOne(long id) {
        return jiekuanMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新借款
     * @param fukuan
     * @return
     */
    @Transactional(readOnly = false)
    public int update(Jiekuan fukuan) {
        return jiekuanMapper.updateByPrimaryKeySelective(fukuan);
    }

    /**
     * 删除借款记录
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(long id) {

        // 1. 找出借款
        Jiekuan jiekuan = jiekuanMapper.selectByPrimaryKey(id);

        // 2. 找出借款对应的还款
        List<Huankuan> huankuans = jiekuan.getHuankuanList();

        // 3. 删除所有还款， 以及还款对应的还款明细
        for (Huankuan huankuan : huankuans) {
            huankuanMapper.delete(huankuan.getId());
            huankuanMapMapper.deleteByHuankuanId(huankuan.getId());
        }

        // 4. 删除借款记录
        int rtn = jiekuanMapper.delete(id);

        return rtn;
    }


    /**
     * 找出某笔订单的所有借款， 过滤掉已经被还完的借款
     * @param orderId
     * @return
     */
    public List<Jiekuan> huankuanUnfinished(Long orderId) {
        PageJiekuanDTO dto = new PageJiekuanDTO();
        dto.setHsId(orderId);
        dto.setPageSize(1000000);
        dto.setPageNo(1);

        List<Jiekuan> all = jiekuanMapper.getPage(dto).getResults();

        List<Jiekuan> filter = new ArrayList<>();

        for (Jiekuan jiekuan : all) {
            // 此笔借款关联的还款明细
            List<HuankuanMap> huankuanMaps = huankuanMapMapper.getListByJiekuanId(jiekuan.getId());
            BigDecimal total = huankuanMaps.stream()
                    .map(HuankuanMap::getPrincipal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (total.equals(jiekuan.getAmount())) {
                continue;
            }

            // 借款的还款明细
            jiekuan.setHuankuanMapList(huankuanMaps);

            // 此笔借款关联的还款
            jiekuan.setHuankuanList(huankuanMapper.getListByJiekuanId(jiekuan.getId()));
            filter.add(jiekuan);
        }
        return filter;
    }
}
