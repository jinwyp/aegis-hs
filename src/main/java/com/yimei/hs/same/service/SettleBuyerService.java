package com.yimei.hs.same.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageSettleBuyerDTO;
import com.yimei.hs.same.entity.*;
import com.yimei.hs.same.mapper.HuikuanMapper;
import com.yimei.hs.same.mapper.OrderConfigMapper;
import com.yimei.hs.same.mapper.SettleBuyerMapper;
import com.yimei.hs.ying.entity.AnalysisData;
import com.yimei.hs.ying.mapper.YingAnalysisDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@Service
@Transactional(readOnly = true)
public class SettleBuyerService {

    private static final Logger logger = LoggerFactory.getLogger(SettleBuyerService.class);

    @Autowired
    SettleBuyerMapper settleBuyerMapper;

    @Autowired
    YingAnalysisDataMapper yingAnalysisDataMapper;

    @Autowired
    HuikuanMapper huikuanMapper;

    @Autowired
    OrderConfigMapper orderConfigMapper;

    /**
     * @param pageSettleBuyerDTO
     * @return
     */
    public Page<SettleBuyer> getPage(PageSettleBuyerDTO pageSettleBuyerDTO) {
        return settleBuyerMapper.getPage(pageSettleBuyerDTO);
    }

    /**
     * 应收-下游结算 || 仓押-上游结算
     *
     * @return
     */
    @Transactional(readOnly = false)
    public int create(SettleBuyer settleBuyer) {
        int rtn = settleBuyerMapper.insert(settleBuyer);
        if (rtn != 1) {
            return 0;
        }
        return rtn;
    }

    /**
     * @param id
     * @return
     */
    public SettleBuyer findOne(long id) {
        return settleBuyerMapper.selectByPrimaryKey(id);
    }


    /**
     * @param settleBuyer
     * @return
     */
    @Transactional(readOnly = false)
    public int update(SettleBuyer settleBuyer) {
        return settleBuyerMapper.updateByPrimaryKeySelective(settleBuyer);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(long id) {
        return settleBuyerMapper.delete(id);
    }

    public List<SettleBuyer> selectByOrderIdAndHsId(Long morderId, Long hsId) {
        return settleBuyerMapper.selectByOrderIdAndHsId(morderId,hsId);
    }


    public SettleSellerInfo findSettleInfo(long orderId, long hsId, BusinessType businessType, @Null String hsMonth){
        SettleSellerInfo settleSellerInfo = new SettleSellerInfo();

        if (BusinessType.ying.equals(businessType)) {

            AnalysisData analysisData = yingAnalysisDataMapper.findOneV1040ying(orderId, hsId);

            settleSellerInfo.setHasSettled(true);
            settleSellerInfo.setHsId(hsId);
            settleSellerInfo.setOrderId(orderId);
            settleSellerInfo.setHsMonth(hsMonth);
            settleSellerInfo.setTotalBuyerNums(analysisData.getFinalSettleAmount());
            settleSellerInfo.setPurchaseCargoAmountOfMoney(analysisData.getSaleCargoAmountofMoney());

        } else {

            AnalysisData analysisDataPayCargoAmount = yingAnalysisDataMapper.findOneV1014(orderId, hsId);


            AnalysisData analysisHuikuanAmount = yingAnalysisDataMapper.findOneV1013(orderId, hsId);

            if (analysisDataPayCargoAmount == null || analysisHuikuanAmount == null) {
                return null;
            }
            BigDecimal payCargoAmount = analysisDataPayCargoAmount.getPayCargoAmount();
            BigDecimal totalHuikuanPaymentMoney = analysisHuikuanAmount.getTotalHuikuanPaymentMoney();
            if (totalHuikuanPaymentMoney.compareTo(payCargoAmount) != -1) {
                settleSellerInfo.setHasSettled(true);
                settleSellerInfo.setHsId(hsId);
                settleSellerInfo.setOrderId(orderId);
                settleSellerInfo.setHsMonth(hsMonth);

                settleSellerInfo.setPurchaseCargoAmountOfMoney(yingAnalysisDataMapper.findOneV1044cang(orderId, hsId).getSaleCargoAmountofMoney());
                settleSellerInfo.setTotalBuyerNums(yingAnalysisDataMapper.findOneV1024(orderId, hsId).getTotalBuyerNums());
                List<Huikuan> huikuans = huikuanMapper.loadAll(orderId);
                if (huikuans != null && huikuans.size() > 0) {
                    settleSellerInfo.setLastHuikuanDate(huikuans.get(huikuans.size() - 1).getHuikuanDate());
                }
            } else {
                settleSellerInfo.setHasSettled(false);
                settleSellerInfo.setTotalBuyerNums(BigDecimal.ZERO);
                settleSellerInfo.setPurchaseCargoAmountOfMoney(BigDecimal.ZERO);
            }


        }

        return settleSellerInfo;
    }

    public List<SettleSellerInfo> findAllSettleInfo(long orderId, BusinessType businessType) {

        List<OrderConfig> orderConfigs=orderConfigMapper.getList(orderId);
        List<SettleSellerInfo> settleSellerInfos = new ArrayList<SettleSellerInfo>();
        for (OrderConfig orderConfig : orderConfigs) {
            settleSellerInfos.add(findSettleInfo(orderId, orderConfig.getId(), businessType,orderConfig.getHsMonth()));
        }
        return settleSellerInfos;
    }

}
