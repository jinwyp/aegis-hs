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

        for (Jiekuan jiekuan : page.getResults()) {
            // 1. 关联借款对应的还款
            jiekuan.setHuankuanList(huankuanMapper.getListByJiekuanId(jiekuan.getId()));
            // 2. 关联借款对应的还款明细
            jiekuan.setHuankuanMapList(huankuanMapMapper.getListByJiekuanId(jiekuan.getId()));
            if (jiekuan.getHuankuanMapList() != null&&jiekuan.getHuankuanMapList().size()>0){
                jiekuan.setLoanStatus(
                        (jiekuan.getAmount().compareTo(jiekuan.getHuankuanMapList().stream()
                                .map(HuankuanMap::getPrincipal)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)) == 1 ? "部分已还" : " 已还"));
            } else {
                jiekuan.setLoanStatus("未还");
            }

        }

        return page;
    }

    /**
     * 找出当前订单的尚未被还款对应完的借款
      * @param orderId
     *  @Param  mainAccoutId
     *  @Param hsId 0 为不查询hsid
     * @return
     */
    public List<Jiekuan> getListUnfinished(long orderId,long mainAccoutId,long hsId) {
        // 过滤该订单 非主账务公司的借款1
        PageJiekuanDTO dto = new PageJiekuanDTO();
        dto.setPageNo(1);
        dto.setPageSize(100000000);
        dto.setOrderId(orderId);
        if (hsId!=0) {
            dto.setHsId(hsId);
        }

        List<Jiekuan> all = jiekuanMapper.getlimitInnerCapital(orderId, mainAccoutId);
        List<Jiekuan> filter = new ArrayList<>();

        for (Jiekuan jiekuan : all) {
            List<HuankuanMap> huankuanMaps = huankuanMapMapper.getListByJiekuanId(jiekuan.getId());

            BigDecimal total = huankuanMaps.stream()
                    .map(HuankuanMap::getPrincipal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if ( jiekuan.getAmount().equals(total)) {
                continue;
            }

            jiekuan.setHuankuanMapList(huankuanMaps);
            jiekuan.setHuankuanTotal(total);
            jiekuan.setHuankuanList(huankuanMapper.getListByJiekuanId(jiekuan.getId()));

            filter.add(jiekuan);
        }

        return filter;
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
     * @return
     */
    @Transactional(readOnly = false)
    public int update(Jiekuan j) {
        // 1. 找出借款
        Jiekuan jiekuan = jiekuanMapper.selectByPrimaryKey(j.getId());

        // 2. 找出借款对应的还款
        List<Huankuan> huankuans = jiekuan.getHuankuanList();

        // 3. 删除所有还款， 以及还款对应的还款明细
        for (Huankuan huankuan : huankuans) {
            huankuanMapper.delete(huankuan.getId());
            huankuanMapMapper.deleteByHuankuanId(huankuan.getId());
        }

        // 4. 删除借款记录
        int rtn = jiekuanMapper.updateByPrimaryKey(j);

        return rtn;
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
        List<HuankuanMap> huankuanMaps = huankuanMapMapper.getListByJiekuanId(id);

        // 3. 删除所有还款， 以及还款对应的还款明细
        for (HuankuanMap huankuanMap : huankuanMaps) {
            huankuanMapper.delete(huankuanMap.getHuankuanId());
            huankuanMapMapper.deleteByHuankuanId(huankuanMap.getId());
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

        dto.setOrderId(orderId);
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

            jiekuan.setHuankuanTotal(total);
            // 借款的还款明细
            jiekuan.setHuankuanMapList(huankuanMaps);

            // 此笔借款关联的还款
            jiekuan.setHuankuanList(huankuanMapper.getListByJiekuanId(jiekuan.getId()));
            filter.add(jiekuan);
        }
        return filter;
    }
}
