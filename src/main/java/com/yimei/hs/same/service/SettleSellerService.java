package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageSettleSellerDTO;
import com.yimei.hs.same.entity.*;
import com.yimei.hs.same.mapper.HuikuanMapper;
import com.yimei.hs.same.mapper.OrderConfigMapper;
import com.yimei.hs.same.mapper.SettleSellerMapper;
import com.yimei.hs.ying.entity.AnalysisData;
import com.yimei.hs.ying.mapper.YingAnalysisDataMapper;
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
public class SettleSellerService {

    private static final Logger logger = LoggerFactory.getLogger(SettleSellerService.class);

    @Autowired
    SettleSellerMapper settleSellerMapper;

    @Autowired
    YingAnalysisDataMapper yingAnalysisDataMapper;

    @Autowired
    HuikuanMapper huikuanMapper;

    @Autowired
    OrderConfigMapper configMapper;


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

    public SettleSellerInfo findSettleInfo(long orderId, long hsId, BusinessType businessType){
        SettleSellerInfo settleSellerInfo = new SettleSellerInfo();

         AnalysisData analysisDataPayCargoAmount = yingAnalysisDataMapper.findOneV1014(orderId, hsId);
         AnalysisData analysisHuikuanAmount = yingAnalysisDataMapper.findOneV1013(orderId, hsId);

        if (analysisDataPayCargoAmount==null ||analysisHuikuanAmount==null) {
            return null;
        }
         BigDecimal payCargoAmount= analysisDataPayCargoAmount.getPayCargoAmount();
         BigDecimal totalHuikuanPaymentMoney =analysisHuikuanAmount.getTotalHuikuanPaymentMoney();
        if (totalHuikuanPaymentMoney.compareTo(payCargoAmount) != -1) {
            settleSellerInfo.setHasSettled(true);
            settleSellerInfo.setHsId(hsId);
            settleSellerInfo.setOrderId(orderId);
            if (businessType.equals(BusinessType.ying)) {
                settleSellerInfo.setPurchaseCargoAmountOfMoney(yingAnalysisDataMapper.findOneV1046ying(orderId, hsId).getPurchaseCargoAmountOfMoney());
            } else if (businessType.equals(BusinessType.cang
            )) {
                settleSellerInfo.setPurchaseCargoAmountOfMoney(yingAnalysisDataMapper.findOneV1046cang(orderId, hsId).getPurchaseCargoAmountOfMoney());
            }
            settleSellerInfo.setTotalBuyerNums(yingAnalysisDataMapper.findOneV1024(orderId, hsId).getTotalBuyerNums());
            List<Huikuan> huikuans=huikuanMapper.loadAll(orderId);
            if (huikuans != null && huikuans.size() > 0) {
                settleSellerInfo.setLastHuikuanDate(huikuans.get(huikuans.size() - 1).getHuikuanDate());
            }
        } else {
            settleSellerInfo.setHasSettled(false);
            settleSellerInfo.setTotalBuyerNums(BigDecimal.ZERO);
            settleSellerInfo.setPurchaseCargoAmountOfMoney(BigDecimal.ZERO);
        }



        return settleSellerInfo;
    }

    public List<SettleSellerInfo> findAllSettleInfo(long orderId, BusinessType businessType) {

        List<OrderConfig> orderConfigs=configMapper.getList(orderId);
        List<SettleSellerInfo> settleSellerInfos = new ArrayList<SettleSellerInfo>();
        for (OrderConfig orderConfig : orderConfigs) {
            settleSellerInfos.add(findSettleInfo(orderId, orderConfig.getId(), businessType));
        }
        return settleSellerInfos;
    }
}
