package com.yimei.hs.ying.service;

import com.yimei.hs.same.entity.*;
import com.yimei.hs.same.mapper.HuankuanMapMapper;
import com.yimei.hs.same.mapper.OrderMapper;
import com.yimei.hs.same.mapper.OrderPartyMapper;
import com.yimei.hs.ying.entity.YingAnalysisData;
import com.yimei.hs.ying.entity.YingBail;
import com.yimei.hs.ying.mapper.YingAnalysisDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

@Service
@Transactional(readOnly = true)
public class YingDataAnalysisService {

    @Autowired
    YingAnalysisDataMapper yingAnalysisDataMapper;

    @Autowired
    HuankuanMapMapper huankuanMapMapper;

    @Autowired
    OrderPartyMapper orderPartyMapper;

    @Autowired
    OrderMapper orderMapper;


    BigDecimal matchedUnloadEstimateCost = new BigDecimal("0");
    List<InvoiceAnalysis> invoiceAnalyses;
    public YingAnalysisData findOne(Long morderId, long hsId) {
        BigDecimal unLoadEstimateCosts = new BigDecimal("0");
        //借款预估成本列表
        List<Jiekuan> unMathchjiekuans = removeMatchList(morderId, hsId);
        if (unMathchjiekuans!=null && unMathchjiekuans.size()>0) {

            for (Jiekuan jiekuan : unMathchjiekuans) {
                unLoadEstimateCosts = unLoadEstimateCosts.add(jiekuan.getLoadEstimateCost());
            }
        }

        //未匹配完成的借款预估成本
        unLoadEstimateCosts = unLoadEstimateCosts.add(matchedUnloadEstimateCost);
        YingAnalysisData yingAnalysisData = yingAnalysisDataMapper.findOne(morderId, hsId);


        //** 获取贸易公司已收到发票进项数量
        List<InvoiceAnalysis> invoiceAnalysisList = getDate(morderId);
        BigDecimal tradingCompanyInTypeNum= invoiceAnalysisList.stream().map(m -> m.getTotalAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal tradingCompanyInTpeMoneyAmount =invoiceAnalysisList.stream().map(m -> m.getTotalPriceTax()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal invoicedMoneyAmount=invoiceAnalyses.stream().map(m -> m.getTotalPriceTax()).reduce(BigDecimal.ZERO, BigDecimal::add);

        if (yingAnalysisData != null) {
            yingAnalysisData.setTotalUnrepaymentEstimateCost(unLoadEstimateCosts);
            yingAnalysisData.setInvoicedMoneyAmount(invoicedMoneyAmount);
            yingAnalysisData.setTradingCompanyInTypeNum(tradingCompanyInTypeNum);
            yingAnalysisData.setTradingCompanyInTpeMoneyAmount(tradingCompanyInTpeMoneyAmount);
        }
        return yingAnalysisData;
    }

    /**
     * 获取核算月所有借款
     *
     * @param orderId
     * @param hsId
     * @return
     */
    public List<Jiekuan> findJiekuanByhsId(Long orderId, Long hsId) {

        return yingAnalysisDataMapper.findJiekuanByhsId(orderId, hsId);
    }

    public List<Jiekuan> removeMatchList(Long morderId, long hsId) {
        List<Jiekuan> unMathchjiekuans = new ArrayList<Jiekuan>();
        List<Jiekuan> allJiekuans = findJiekuanByhsId(morderId, hsId);
        List<Jiekuan> mathchedjiekuans = new ArrayList<Jiekuan>();

        if (allJiekuans == null || allJiekuans.size() == 0) {
            return null;
        }
        for (Jiekuan jiekuan : allJiekuans) {
            List<HuankuanMap> huankuanMaps = huankuanMapMapper.getListByJiekuanId(jiekuan.getId());
            BigDecimal totalhuankuan = huankuanMaps.stream().map(m -> m.getPrincipal()).reduce(BigDecimal.ZERO, BigDecimal::add);
            jiekuan.setHuankuanTotal(totalhuankuan);
            //还款总额大于1
            if (totalhuankuan.compareTo(BigDecimal.ZERO) == 1) {
                mathchedjiekuans.add(jiekuan);
                matchedUnloadEstimateCost = jiekuan.getAmount().subtract(jiekuan.getHuankuanTotal()).multiply(jiekuan.getUseInterest()).multiply(new BigDecimal(jiekuan.getUseDays())).divide(new BigDecimal(360), 2, ROUND_HALF_DOWN);
                ;
            } else {
                unMathchjiekuans.add(jiekuan);
            }
        }

        return unMathchjiekuans;
    }

    //** 获取贸易公司已收到发票进项数量、金额

    public List<InvoiceAnalysis> getDate(Long morderId) {

        Order order= orderMapper.selectByPrimaryKey(morderId);
        //获得该订单 贸易公司列表
        List<OrderParty> orderParties = orderPartyMapper.findListByCustTypeAndOrderId("TRAFFICKCER", morderId);
        StringBuffer stringBuffer = new StringBuffer();
        for (OrderParty orderParty:orderParties) {
            stringBuffer.append(orderParty.getCustomerId());
        }
        List<InvoiceAnalysis> invoiceAnalyseNeed = new ArrayList<InvoiceAnalysis>();
        //获得所有开票单位为上游的发票列表
         invoiceAnalyses = yingAnalysisDataMapper.findByOrderIdOpenCompanyId(order.getUpstreamId(),morderId);

        //过滤非贸易公司的发票记录
        for (InvoiceAnalysis invoiceAnalysis :invoiceAnalyses) {
            if (stringBuffer.toString().contains(invoiceAnalysis.getReceiverId().toString())) {
                invoiceAnalyseNeed.add(invoiceAnalysis);
            }
        }

        return invoiceAnalyseNeed;
    }

}
