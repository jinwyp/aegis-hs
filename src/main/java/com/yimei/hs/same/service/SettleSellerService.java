package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageSettleSellerDTO;
import com.yimei.hs.same.entity.SettleSeller;
import com.yimei.hs.same.mapper.SettleSellerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
@Transactional(readOnly = true)
public class SettleSellerService {

    private static final Logger logger = LoggerFactory.getLogger(SettleSellerService.class);

    @Autowired
    SettleSellerMapper settleSellerMapper;

    /**
     * find settles by orderid
     *
     * @param orderId
     * @return
     */
    public List<SettleSeller> getAllUpStreamSettles(Long orderId) {
        return settleSellerMapper.selectByOrderId(orderId);
    }

    /**
     * @param pageSettleSellerDTO
     * @return
     */
    public Page<SettleSeller> getPage(PageSettleSellerDTO pageSettleSellerDTO) {
        return settleSellerMapper.getPage(pageSettleSellerDTO);
    }

    /**
     * 
     * @param settleSeller
     * @return
     */
    @Transactional(readOnly = false)
    public int create(SettleSeller settleSeller) {
        return settleSellerMapper.insert(settleSeller);
    }

    /**
     * @param id
     * @return
     */
    public SettleSeller findOne(long id) {
        return settleSellerMapper.selectByPrimaryKey(id);
    }

    /**
     * @param settleSeller
     * @return
     */
    @Transactional(readOnly = false)
    public int update(SettleSeller settleSeller) {
        return settleSellerMapper.updateByPrimaryKeySelective(settleSeller);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(long id) {
        return settleSellerMapper.delete(id);
    }

    public boolean selectHsAndOrderId(Long orderId, Long hsId) {
        return settleSellerMapper.findByOrderIdAndHsId(orderId,hsId);
    }
}
