package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageSettleSellerDTO;
import com.yimei.hs.same.entity.SettleSeller;
import com.yimei.hs.same.mapper.SettleSellerMapper;
import com.yimei.hs.ying.service.YingLogService;
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

    @Autowired
    private YingLogService yingLogService;



    /**
     * @param pageSettleSellerDTO
     * @return
     */
    public Page<SettleSeller> getPageUpstream(PageSettleSellerDTO pageSettleSellerDTO) {
        return settleSellerMapper.getPage(pageSettleSellerDTO);
    }





    @Transactional(readOnly = false)
    public int createUpstream(SettleSeller yingSettleUpstream) {
        return settleSellerMapper.insert(yingSettleUpstream);
    }


    /**
     * @param id
     * @return
     */
    public SettleSeller findUpstream(long id) {
        return settleSellerMapper.selectByPrimaryKey(id);
    }

    /**
     * @param yingSettleUpstream
     * @return
     */
    @Transactional(readOnly = false)
    public int updateUpstream(SettleSeller yingSettleUpstream) {
        return settleSellerMapper.updateByPrimaryKeySelective(yingSettleUpstream);
    }







    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteUpstream(long id) {
        return settleSellerMapper.delete(id);
    }
}
