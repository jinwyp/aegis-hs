package com.yimei.hs.same.service;

import com.yimei.hs.cang.entity.CangAnalysisData;
import com.yimei.hs.cang.mapper.CangAnalysisDataMapper;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.entity.Jiekuan;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.entity.OrderConfig;
import com.yimei.hs.ying.entity.AnalysisData;
import com.yimei.hs.ying.mapper.YingAnalysisDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class DataAnalysisService {

    @Autowired
    YingAnalysisDataMapper yingAnalysisDataMapper;

    @Autowired
    CangAnalysisDataMapper cangAnalysisDataMapper;

    @Autowired
    OrderConfigService orderConfigService;

    @Autowired
    JiekuanService jiekuanService;

    @Autowired
    OrderService orderService;


    public AnalysisData findOneYing(Long morderId, long hsId) {

        Order order = orderService.findOne(morderId);
//        获得未还款金额(借款)
        List<Jiekuan> jiekuans = jiekuanService.getListUnfinished(morderId, order.getMainAccounting(), hsId);
        BigDecimal nonRepaymentLoanMoney = jiekuans.stream().map(Jiekuan::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add).
                subtract(
                        jiekuans.stream().map(Jiekuan::getHuankuanTotal).reduce(BigDecimal.ZERO, BigDecimal::add));


        OrderConfig orderConfigBase = yingAnalysisDataMapper.findOneVBase(morderId, hsId);
        AnalysisData yingAnalysisDatav1001 = yingAnalysisDataMapper.findOneV1001(morderId, hsId);
        AnalysisData yingAnalysisDatav1004 = yingAnalysisDataMapper.findOneV1004(morderId, hsId);
        AnalysisData yingAnalysisDatav1006 = yingAnalysisDataMapper.findOneV1006(morderId, hsId);
        AnalysisData yingAnalysisDatav1007 = yingAnalysisDataMapper.findOneV1007(morderId, hsId);
        AnalysisData yingAnalysisDatav1009 = yingAnalysisDataMapper.findOneV1009(morderId, hsId);
        AnalysisData yingAnalysisDatav1012 = yingAnalysisDataMapper.findOneV1012(morderId, hsId);
        AnalysisData yingAnalysisDatav1013 = yingAnalysisDataMapper.findOneV1013(morderId, hsId);
        AnalysisData yingAnalysisDatav1014 = yingAnalysisDataMapper.findOneV1014(morderId, hsId);
        AnalysisData yingAnalysisDatav1015 = yingAnalysisDataMapper.findOneV1015(morderId, hsId);
        AnalysisData yingAnalysisDatav1016 = yingAnalysisDataMapper.findOneV1016(morderId, hsId);
        AnalysisData yingAnalysisDatav1017 = yingAnalysisDataMapper.findOneV1017(morderId, hsId);
        AnalysisData yingAnalysisDatav1018 = yingAnalysisDataMapper.findOneV1018(morderId, hsId);
//        AnalysisData yingAnalysisDatav1019 = yingAnalysisDataMapper.findOneV1019(morderId, hsId);
        AnalysisData yingAnalysisDatav1020 = yingAnalysisDataMapper.findOneV1020(morderId, hsId);
        AnalysisData yingAnalysisDatav1021 = yingAnalysisDataMapper.findOneV1021(morderId, hsId);
        AnalysisData yingAnalysisDatav1023 = yingAnalysisDataMapper.findOneV1023(morderId, hsId);
        AnalysisData yingAnalysisDatav1024 = yingAnalysisDataMapper.findOneV1024(morderId, hsId);
        AnalysisData yingAnalysisDatav1027 = yingAnalysisDataMapper.findOneV1027(morderId, hsId);
        AnalysisData yingAnalysisDatav1035 = yingAnalysisDataMapper.findOneV1035(morderId, hsId);
        AnalysisData yingAnalysisDatav1037 = yingAnalysisDataMapper.findOneV1037(morderId, hsId);
        AnalysisData yingAnalysisDatav1039 = yingAnalysisDataMapper.findOneV1039(morderId, hsId);
        AnalysisData yingAnalysisDatav2001 = yingAnalysisDataMapper.findOneV2001(morderId, hsId);
        AnalysisData yingAnalysisDatav2004 = yingAnalysisDataMapper.findOneV2004(morderId, hsId);
        AnalysisData yingAnalysisDatav2008 = yingAnalysisDataMapper.findOneV2008(morderId, hsId);
        AnalysisData yingAnalysisDatav1041 = yingAnalysisDataMapper.findOneV1040ying(morderId, hsId);
        AnalysisData yingAnalysisDatav1045 = yingAnalysisDataMapper.findOneV1045(morderId, hsId);
        AnalysisData yingAnalysisDatav1046 = yingAnalysisDataMapper.findOneV1046ying(morderId, hsId);
        AnalysisData yingAnalysisDatav1047 = yingAnalysisDataMapper.findOneV1047(morderId, hsId);
        AnalysisData yingAnalysisDatav1048 = yingAnalysisDataMapper.findOneV1048ying(morderId, hsId);
        AnalysisData yingAnalysisDatav1049 = yingAnalysisDataMapper.findOneV1049ying(morderId, hsId);
        AnalysisData yingAnalysisDatav1050 = yingAnalysisDataMapper.findOneV1050ying(morderId, hsId);
        AnalysisData yingAnalysisDatav1051 = yingAnalysisDataMapper.findOneV1051ying(morderId, hsId);
        AnalysisData yingAnalysisDatav1052 = yingAnalysisDataMapper.findOneV1052ying(morderId, hsId);
        AnalysisData yingAnalysisDatav1054 = yingAnalysisDataMapper.findOneV1054ying(morderId, hsId);
        AnalysisData yingAnalysisDatav1055 = yingAnalysisDataMapper.findOneV1055ying(morderId, hsId);
        AnalysisData yingAnalysisDatav1058 = yingAnalysisDataMapper.findOneV1058ying(morderId, hsId);
        AnalysisData yingAnalysisDatav1059 = yingAnalysisDataMapper.findOneV1059ying(morderId, hsId);
        AnalysisData yingAnalysisDatav1060 = yingAnalysisDataMapper.findOneV1060ying(morderId, hsId);
        AnalysisData yingAnalysisDatav1061 = yingAnalysisDataMapper.findOneV1061ying(morderId, hsId);
        AnalysisData yingAnalysisDatav1063 = yingAnalysisDataMapper.findOneV1063ying(morderId, hsId);
        AnalysisData yingAnalysisDatav1066 = yingAnalysisDataMapper.findOneV1066ying(morderId, hsId);
        AnalysisData yingAnalysisDatav2010 = yingAnalysisDataMapper.findOneV2010ying(morderId, hsId);
        AnalysisData yingAnalysisDos1 = yingAnalysisDataMapper.findOneShowDos1ying(morderId, hsId);
        AnalysisData yingAnalysisDos2 = yingAnalysisDataMapper.findOneShowDos2(morderId, hsId);
        AnalysisData yingAnalysisDos3 = yingAnalysisDataMapper.findOneShowDos3(morderId);
//【1059】不含税收入 - 【1060】不含税成本 - 【1062】税金及附加 - 【1063】印花税 - （【1028】含税汽运费 + 【1029】含税水运费 + 【1030】含税火运费）／1.11 - 【1031】监管费 ／1.06 - 【1031】服务费 ／1.06 - 【1033】业务费
        BigDecimal opreationCrocsProfile = yingAnalysisDatav1059.getWithoutTaxIncome().
                subtract(yingAnalysisDatav1060.getWithoutTaxCost()).
                subtract(yingAnalysisDatav1061.getAdditionalTax()).
                subtract(yingAnalysisDatav1063.getStampDuty()).
                subtract(yingAnalysisDatav1027.getSalesFeeAmount()).
                subtract((yingAnalysisDatav1027.getHsqyFee().add(yingAnalysisDatav1027.getHssyFee().add(yingAnalysisDatav1027.getHshyFee()))).divide(new BigDecimal("1.11"))).
                subtract(yingAnalysisDatav1027.getSuperviseFee().divide(new BigDecimal("1.06"))).
                subtract(yingAnalysisDatav1027.getServiceFee().divide(new BigDecimal("1.06")));

        // 1039】占压表已开票金额+（（【2002】已到场数量 - 【1026】结算扣吨合计 - 【1024】买方已结算数量） * 【核算月配置】加权单价+【1025】买方已结算金额-【1039】占压表已开票金额/【核算月配置】最高预付款比例）*【核算月配置】未开票款付款比例-汇总：付款用途 = “货款”的付款金额-->

//        BigDecimal amountCargoOfThisTime = yingAnalysisDatav1039.getInvoicedMoneyAmount()
//                .add(
//                        yingAnalysisDatav2001.getTotalArriveNum().
//                                subtract(
//                                        yingAnalysisDatav1024.getTotalBuyerNums()).
//                                subtract(yingAnalysisDatav1024.getTotalBuyersettleGap())
//                ).multiply(orderConfigBase.getTradeAddPrice().add(yingAnalysisDatav1024.getTotalBuyerMoney())
//                ).subtract(yingAnalysisDatav1039.getInvoicedMoneyAmount().multiply(orderConfigBase.getMaxPrepayRate())).multiply(orderConfigBase.getUnInvoicedRate()
//
//                ).subtract(yingAnalysisDatav1001.getTotalPayGoodsFee());

        AnalysisData yingAnalysisData = new AnalysisData() {{
            setOrderId(orderConfigBase.getOrderId());
            setHsId(orderConfigBase.getHsId());
            setHsMonth(orderConfigBase.getHsMonth());
            setTotalPayTrafficFee(yingAnalysisDatav1001.getTotalPayTrafficFee());
            setTotalTradeGapFee(yingAnalysisDatav1001.getTotalTradeGapFee());

            setTotalPaymentAmount(yingAnalysisDatav1001.getTotalPaymentAmount());
            setTotalLoadMoney(yingAnalysisDatav1004.getTotalLoadMoney());
            setTotalUnrepaymentEstimateCost((yingAnalysisDatav1006 == null ? new BigDecimal("0.00") : yingAnalysisDatav1006.getTotalUnrepaymentEstimateCost()));
            setTotalRepaymentPrincipeAmount(yingAnalysisDatav1007.getTotalRepaymentPrincipeAmount());
            setTotalRepaymentPrincipeAmount(yingAnalysisDatav1007.getTotalRepaymentPrincipeAmount());
            setTotalRepaymentInterest(yingAnalysisDatav1007.getTotalRepaymentInterest());
            setTotalServiceCharge(yingAnalysisDatav1007.getTotalServiceCharge());
            setTotalUnpayPrincipal((yingAnalysisDatav1009 == null ? new BigDecimal("0.00") : yingAnalysisDatav1009.getTotalUnpayPrincipal()));
            setTotalUnpayInterest((yingAnalysisDatav1009 == null ? new BigDecimal("0.00") : yingAnalysisDatav1009.getTotalUnpayInterest()));
            setTotalUnpayFee((yingAnalysisDatav1009 == null ? new BigDecimal("0.00") : yingAnalysisDatav1009.getTotalUnpayFee()));
            setOutCapitalAmout(yingAnalysisDatav1012.getOutCapitalAmout());
            setTotalHuikuanPaymentMoney((yingAnalysisDatav1013 == null ? new BigDecimal("0.00") : yingAnalysisDatav1013.getTotalHuikuanPaymentMoney()));
            setPayCargoAmount(yingAnalysisDatav1014.getPayCargoAmount());
            setUnpaymentMoney(yingAnalysisDatav1015.getUnpaymentMoney());
            setUnpaymentEstimateProfile(yingAnalysisDatav1016.getUnpaymentEstimateProfile());
            setInterestDays(yingAnalysisDatav1017.getInterestDays());
            setActualUtilizationRate(yingAnalysisDatav1018.getActualUtilizationRate());
//            setRate(yingAnalysisDatav1019.getRate());
            setTotalPaymentedRateMoney(yingAnalysisDatav1020.getTotalPaymentedRateMoney());
            setContractRateProfile(yingAnalysisDatav1021.getContractRateProfile());
            setTiexianRateAmount(yingAnalysisDatav1023.getTiexianRateAmount());
            setTotalBuyerMoney(yingAnalysisDatav1024.getTotalBuyerMoney());
            setTotalBuyerNums(yingAnalysisDatav1024.getTotalBuyerNums());
            setTotalBuyersettleGap(yingAnalysisDatav1024.getTotalBuyersettleGap());
            setDsddFee(yingAnalysisDatav1027.getDsddFee());
            setHshyFee(yingAnalysisDatav1027.getHshyFee());
            setHsqyFee(yingAnalysisDatav1027.getHsqyFee());
            setHssyFee(yingAnalysisDatav1027.getHssyFee());
            setBusinessFee(yingAnalysisDatav1027.getBusinessFee());
            setServiceFee(yingAnalysisDatav1027.getServiceFee());
            setSuperviseFee(yingAnalysisDatav1027.getSuperviseFee());
            setSalesFeeAmount(yingAnalysisDatav1027.getSalesFeeAmount());
            setTradingCompanyInTpeMoneyAmount((yingAnalysisDatav1035 == null ? new BigDecimal("0.00") : yingAnalysisDatav1035.getTradingCompanyInTpeMoneyAmount()));
            setTradingCompanyInTypeNum((yingAnalysisDatav1035 == null ? new BigDecimal("0.00") : yingAnalysisDatav1035.getTradingCompanyInTypeNum()));
            setTotalCCSIntypeMoney((yingAnalysisDatav1037 == null ? new BigDecimal("0.00") : yingAnalysisDatav1037.getTotalCCSIntypeMoney()));
            setTotalCSSIntypeNumber((yingAnalysisDatav1037 == null ? new BigDecimal("0.00") : yingAnalysisDatav1037.getTotalCSSIntypeNumber()));
            setInvoicedMoneyAmount((yingAnalysisDatav1039 == null ? new BigDecimal("0.00") : yingAnalysisDatav1039.getInvoicedMoneyAmount()));
            setInvoicedMoneyNum((yingAnalysisDatav1039 == null ? new BigDecimal("0.00") : yingAnalysisDatav1039.getInvoicedMoneyNum()));
            setTotalArriveNum(yingAnalysisDatav2001.getTotalArriveNum());
            setTotalUnarriveNum(yingAnalysisDatav2001.getTotalUnarriveNum());
            setTotalFayunNum(yingAnalysisDatav2001.getTotalFayunNum());
            setTotalUpstreamBail(yingAnalysisDatav2004.getTotalUpstreamBail());
            setTotalRefundUpBail(yingAnalysisDatav2004.getTotalRefundUpBail());
            setTotalPayDownBail(yingAnalysisDatav2004.getTotalPayDownBail());
            setTotalRefundDownBail(yingAnalysisDatav2004.getTotalRefundDownBail());
            setBalanceDownStreamBail(yingAnalysisDatav2008.getBalanceDownStreamBail());
            setBalanceUpstreamBail(yingAnalysisDatav2008.getBalanceUpstreamBail());
            setFinalSettleAmount(yingAnalysisDatav1041.getFinalSettleAmount());
            setUnsettlerBuyerMoneyAmount(yingAnalysisDatav1041.getUnsettlerBuyerMoneyAmount());
            setUnsettlerBuyerNumber(yingAnalysisDatav1041.getUnsettlerBuyerNumber());
            setTradingCompanyAddMoney(yingAnalysisDatav1041.getTradingCompanyAddMoney());
            setSaleCargoAmountofMoney(yingAnalysisDatav1041.getSaleCargoAmountofMoney());
            setCcsProfile(yingAnalysisDatav1045.getCcsProfile());
            setPurchaseCargoAmountOfMoney(yingAnalysisDatav1046.getPurchaseCargoAmountOfMoney());
            setExternalCapitalPaymentAmount(yingAnalysisDatav1047.getExternalCapitalPaymentAmount());
            setOwnerCapitalPaymentAmount(yingAnalysisDatav1048.getOwnerCapitalPaymentAmount());
            setUpstreamCapitalPressure(yingAnalysisDatav1049.getUpstreamCapitalPressure());
            setPurchaseIncludeTaxTotalAmount(yingAnalysisDatav1046.getPurchaseCargoAmountOfMoney());
            setDownstreamCapitalPressure(yingAnalysisDatav1050.getDownstreamCapitalPressure());
            setCssUninTypeNum(yingAnalysisDatav1051.getCssUninTypeNum());
            setCssUninTypeMoney(yingAnalysisDatav1052.getCssUninTypeMoney());
            setUnInvoicedAmountofMoney(yingAnalysisDatav1052.getUnInvoicedAmountofMoney());
            setYingPrePayment(yingAnalysisDatav1054.getYingPrePayment());
            setSettleGrossProfileNum(yingAnalysisDatav1041.getFinalSettleAmount());
            setSaleIncludeTaxTotalAmount(yingAnalysisDatav1041.getSaleCargoAmountofMoney());
            setTradeCompanyAddMoney(yingAnalysisDatav1058.getTradeCompanyAddMoney());
            setWithoutTaxIncome(yingAnalysisDatav1059.getWithoutTaxIncome());
            setWithoutTaxCost(yingAnalysisDatav1060.getWithoutTaxCost());
            setVat(yingAnalysisDatav1061.getVat());
            setAdditionalTax(yingAnalysisDatav1061.getAdditionalTax());
            setStampDuty(yingAnalysisDatav1063.getStampDuty());
            setOpreationCrossProfile(BigDecimal.TEN);
            setCrossProfileATon(BigDecimal.ONE);
            setOwnerCapitalPressure(yingAnalysisDatav1066.getOwnerCapitalPressure());
            setSettledDownstreamHuikuanMoneny(yingAnalysisDatav2010.getSettledDownstreamHuikuanMoneny());
            setOpreationCrossProfile(opreationCrocsProfile);
            setCrossProfileATon((yingAnalysisDatav1055.getSettleGrossProfileNum()).compareTo(BigDecimal.ZERO) == 0 ?
                    new BigDecimal("0.00") : opreationCrocsProfile.divide(yingAnalysisDatav1055.getSettleGrossProfileNum(), 2, BigDecimal.ROUND_HALF_UP));
            //付款相关字段


            setMaximumPaymentAmount(yingAnalysisDos1.getMaximumPaymentAmount());
            setUnitTotalPaymentAmount(yingAnalysisDatav1001.getTotalPaymentAmount().add(yingAnalysisDatav1007.getTotalRepaymentInterest()));
//            业务累计付款金额
            setAccumulativePaymentAmount(yingAnalysisDos3.getAccumulativePaymentAmount());
            setAmountCargoOfThisTime(yingAnalysisDos2.getAmountCargoOfThisTime());


            //借款需要字段
            setNonRepaymentLoanMoney(nonRepaymentLoanMoney);


        }};

        return yingAnalysisData;


    }


    public List<AnalysisData> findYingList(Long morderId) {
        List<OrderConfig> orderConfigs = orderConfigService.getList(morderId);
        List<AnalysisData> yingAnalysisDataList = new ArrayList<AnalysisData>();
        for (OrderConfig config : orderConfigs) {
            yingAnalysisDataList.add(this.findOneYing(morderId, config.getId()));
        }
        return yingAnalysisDataList;
    }


    public AnalysisData findOneCang(Long hsId, Long morderId) {


        Order order = orderService.findOne(morderId);
//        获得未还款金额(借款)
        List<Jiekuan> jiekuans = jiekuanService.getListUnfinished(morderId, order.getMainAccounting(), hsId);
        BigDecimal nonRepaymentLoanMoney = jiekuans.stream().map(Jiekuan::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add).
                subtract(
                        jiekuans.stream().map(Jiekuan::getHuankuanTotal).reduce(BigDecimal.ZERO, BigDecimal::add));


        OrderConfig orderConfigBase = yingAnalysisDataMapper.findOneVBase(morderId, hsId);


        AnalysisData yingAnalysisDatav1001 = yingAnalysisDataMapper.findOneV1001(morderId, hsId);
        AnalysisData yingAnalysisDatav1004 = yingAnalysisDataMapper.findOneV1004(morderId, hsId);
        AnalysisData yingAnalysisDatav1006 = yingAnalysisDataMapper.findOneV1006(morderId, hsId);
        AnalysisData yingAnalysisDatav1007 = yingAnalysisDataMapper.findOneV1007(morderId, hsId);
        AnalysisData yingAnalysisDatav1009 = yingAnalysisDataMapper.findOneV1009(morderId, hsId);
        AnalysisData yingAnalysisDatav1012 = yingAnalysisDataMapper.findOneV1012(morderId, hsId);
        AnalysisData yingAnalysisDatav1013 = yingAnalysisDataMapper.findOneV1013(morderId, hsId);
        AnalysisData yingAnalysisDatav1014 = yingAnalysisDataMapper.findOneV1014(morderId, hsId);
        AnalysisData yingAnalysisDatav1015 = yingAnalysisDataMapper.findOneV1015(morderId, hsId);
        AnalysisData yingAnalysisDatav1016 = yingAnalysisDataMapper.findOneV1016(morderId, hsId);
        AnalysisData yingAnalysisDatav1017 = yingAnalysisDataMapper.findOneV1017(morderId, hsId);
        AnalysisData yingAnalysisDatav1018 = yingAnalysisDataMapper.findOneV1018(morderId, hsId);
//        AnalysisData yingAnalysisDatav1019 = yingAnalysisDataMapper.findOneV1019(morderId, hsId);
        AnalysisData yingAnalysisDatav1020 = yingAnalysisDataMapper.findOneV1020(morderId, hsId);
        AnalysisData yingAnalysisDatav1021 = yingAnalysisDataMapper.findOneV1021(morderId, hsId);
        AnalysisData yingAnalysisDatav1023 = yingAnalysisDataMapper.findOneV1023(morderId, hsId);
        AnalysisData yingAnalysisDatav1024 = yingAnalysisDataMapper.findOneV1024(morderId, hsId);
        AnalysisData yingAnalysisDatav1027 = yingAnalysisDataMapper.findOneV1027(morderId, hsId);
        AnalysisData yingAnalysisDatav1035 = yingAnalysisDataMapper.findOneV1035(morderId, hsId);
        AnalysisData yingAnalysisDatav1037 = yingAnalysisDataMapper.findOneV1037(morderId, hsId);
        AnalysisData yingAnalysisDatav1039 = yingAnalysisDataMapper.findOneV1039(morderId, hsId);
        AnalysisData yingAnalysisDatav3001 = yingAnalysisDataMapper.findOneV3001(morderId, hsId);
        AnalysisData yingAnalysisDatav3003 = yingAnalysisDataMapper.findOneV3003(morderId, hsId);
        AnalysisData yingAnalysisDatav3005 = yingAnalysisDataMapper.findOneV3005(morderId, hsId);
        AnalysisData yingAnalysisDatav3006 = yingAnalysisDataMapper.findOneV3006(morderId, hsId);
        AnalysisData yingAnalysisDatav1041 = yingAnalysisDataMapper.findOneV1040cang(morderId, hsId);
        AnalysisData yingAnalysisDatav1045 = yingAnalysisDataMapper.findOneV1045(morderId, hsId);
        AnalysisData yingAnalysisDatav1046 = yingAnalysisDataMapper.findOneV1046cang(morderId, hsId);
        AnalysisData yingAnalysisDatav1047 = yingAnalysisDataMapper.findOneV1047(morderId, hsId);
        AnalysisData yingAnalysisDatav1048 = yingAnalysisDataMapper.findOneV1048cang(morderId, hsId);
        AnalysisData yingAnalysisDatav1049 = yingAnalysisDataMapper.findOneV1049cang(morderId, hsId);
        AnalysisData yingAnalysisDatav1050 = yingAnalysisDataMapper.findOneV1050cang(morderId, hsId);
        AnalysisData yingAnalysisDatav1051 = yingAnalysisDataMapper.findOneV1051cang(morderId, hsId);
        AnalysisData yingAnalysisDatav1052 = yingAnalysisDataMapper.findOneV1052cang(morderId, hsId);
        AnalysisData yingAnalysisDatav1054 = yingAnalysisDataMapper.findOneV1054cang(morderId, hsId);
        AnalysisData yingAnalysisDatav1056 = yingAnalysisDataMapper.findOneV1056cang(morderId, hsId);
        AnalysisData yingAnalysisDatav1057 = yingAnalysisDataMapper.findOneV1057cang(morderId, hsId);
        AnalysisData yingAnalysisDatav1058 = yingAnalysisDataMapper.findOneV1058cang(morderId, hsId);
        AnalysisData yingAnalysisDatav1059 = yingAnalysisDataMapper.findOneV1059cang(morderId, hsId);
        AnalysisData yingAnalysisDatav1060 = yingAnalysisDataMapper.findOneV1060cang(morderId, hsId);
        AnalysisData yingAnalysisDatav1061 = yingAnalysisDataMapper.findOneV1061cang(morderId, hsId);
        AnalysisData yingAnalysisDatav1063 = yingAnalysisDataMapper.findOneV1063cang(morderId, hsId);


        AnalysisData yingAnalysisDatav1066 = yingAnalysisDataMapper.findOneV1066cang(morderId, hsId);
        AnalysisData yingAnalysisDatav2010 = yingAnalysisDataMapper.findOneV2010cang(morderId, hsId);


        AnalysisData yingAnalysisDos1 = yingAnalysisDataMapper.findOneShowDos1ying(morderId, hsId);
        AnalysisData yingAnalysisDos3 = yingAnalysisDataMapper.findOneShowDos3(morderId);

//        【1059】不含税收入 - 【1060】不含税成本 - 【1062】税金及附加 - 【1063】印花税 - （【1028】含税汽运费 + 【1029】含税水运费 + 【1030】含税火运费）／1.11 - 【1031】监管费 ／1.06 - 【1031】服务费 ／1.06 - 【1033】业务费
        BigDecimal opreationCrocsProfile = yingAnalysisDatav1059.getWithoutTaxIncome().
                subtract(yingAnalysisDatav1060.getWithoutTaxCost()).
                subtract(yingAnalysisDatav1061.getAdditionalTax()).
                subtract(yingAnalysisDatav1063.getStampDuty()).
                subtract(yingAnalysisDatav1027.getSalesFeeAmount()).
                subtract((yingAnalysisDatav1027.getHsqyFee().add(yingAnalysisDatav1027.getHssyFee().add(yingAnalysisDatav1027.getHshyFee()))).divide(new BigDecimal("1.11"))).
                subtract(yingAnalysisDatav1027.getSuperviseFee().divide(new BigDecimal("1.06"))).
                subtract(yingAnalysisDatav1027.getServiceFee().divide(new BigDecimal("1.06")));


        AnalysisData yingAnalysisData = new AnalysisData() {{
            setOrderId(orderConfigBase.getOrderId());
            setHsId(orderConfigBase.getHsId());
            setHsMonth(orderConfigBase.getHsMonth());
            setTotalPayTrafficFee(yingAnalysisDatav1001.getTotalPayTrafficFee());
            setTotalTradeGapFee(yingAnalysisDatav1001.getTotalTradeGapFee());
            setTotalPaymentAmount(yingAnalysisDatav1001.getTotalPaymentAmount());
            setTotalLoadMoney(yingAnalysisDatav1004.getTotalLoadMoney());
            setTotalUnrepaymentEstimateCost((yingAnalysisDatav1006 == null ? new BigDecimal("0.00") : yingAnalysisDatav1006.getTotalUnrepaymentEstimateCost()));
            setTotalRepaymentPrincipeAmount(yingAnalysisDatav1007.getTotalRepaymentPrincipeAmount());
            setTotalRepaymentPrincipeAmount(yingAnalysisDatav1007.getTotalRepaymentPrincipeAmount());
            setTotalRepaymentInterest(yingAnalysisDatav1007.getTotalRepaymentInterest());
            setTotalServiceCharge(yingAnalysisDatav1007.getTotalServiceCharge());
            setTotalUnpayPrincipal((yingAnalysisDatav1009 == null ? new BigDecimal("0.00") : yingAnalysisDatav1009.getTotalUnpayPrincipal()));
            setTotalUnpayInterest((yingAnalysisDatav1009 == null ? new BigDecimal("0.00") : yingAnalysisDatav1009.getTotalUnpayInterest()));
            setTotalUnpayFee((yingAnalysisDatav1009 == null ? new BigDecimal("0.00") : yingAnalysisDatav1009.getTotalUnpayFee()));
            setOutCapitalAmout(yingAnalysisDatav1012.getOutCapitalAmout());
            setTotalHuikuanPaymentMoney(yingAnalysisDatav1013.getTotalHuikuanPaymentMoney());
            setPayCargoAmount(yingAnalysisDatav1014.getPayCargoAmount());
            setUnpaymentMoney(yingAnalysisDatav1015.getUnpaymentMoney());
            setUnpaymentEstimateProfile(yingAnalysisDatav1016.getUnpaymentEstimateProfile());
            setInterestDays(yingAnalysisDatav1017.getInterestDays());
            setActualUtilizationRate(yingAnalysisDatav1018.getActualUtilizationRate());
//            setRate(yingAnalysisDatav1019.getRate());
            setTotalPaymentedRateMoney(yingAnalysisDatav1020.getTotalPaymentedRateMoney());
            setContractRateProfile(yingAnalysisDatav1021.getContractRateProfile());
            setTiexianRateAmount(yingAnalysisDatav1023.getTiexianRateAmount());
            setTotalBuyerMoney(yingAnalysisDatav1024.getTotalBuyerMoney());
            setTotalBuyerNums(yingAnalysisDatav1024.getTotalBuyerNums());
            setTotalBuyersettleGap(yingAnalysisDatav1024.getTotalBuyersettleGap());
            setDsddFee(yingAnalysisDatav1027.getDsddFee());
            setHshyFee(yingAnalysisDatav1027.getHshyFee());
            setHsqyFee(yingAnalysisDatav1027.getHsqyFee());
            setHssyFee(yingAnalysisDatav1027.getHssyFee());
            setBusinessFee(yingAnalysisDatav1027.getBusinessFee());
            setServiceFee(yingAnalysisDatav1027.getServiceFee());
            setSuperviseFee(yingAnalysisDatav1027.getSuperviseFee());
            setSalesFeeAmount(yingAnalysisDatav1027.getSalesFeeAmount());
            setTradingCompanyInTpeMoneyAmount((yingAnalysisDatav1035 == null ? new BigDecimal("0.00") : yingAnalysisDatav1035.getTradingCompanyInTpeMoneyAmount()));
            setTradingCompanyInTypeNum((yingAnalysisDatav1035 == null ? new BigDecimal("0.00") : yingAnalysisDatav1035.getTradingCompanyInTypeNum()));
            setTotalCCSIntypeMoney((yingAnalysisDatav1037 == null ? new BigDecimal("0.00") : yingAnalysisDatav1037.getTotalCCSIntypeMoney()));
            setTotalCSSIntypeNumber((yingAnalysisDatav1037 == null ? new BigDecimal("0.00") : yingAnalysisDatav1037.getTotalCSSIntypeNumber()));
            setInvoicedMoneyAmount((yingAnalysisDatav1039 == null ? new BigDecimal("0.00") : yingAnalysisDatav1039.getInvoicedMoneyAmount()));
            setInvoicedMoneyNum((yingAnalysisDatav1039 == null ? new BigDecimal("0.00") : yingAnalysisDatav1039.getInvoicedMoneyNum()));
            setTotalInstorageAmount(yingAnalysisDatav3001.getTotalInstorageAmount());
            setTotalInstorageNum(yingAnalysisDatav3001.getTotalInstorageNum());
            setInstorageUnitPrice(yingAnalysisDatav3001.getInstorageUnitPrice());
            setTotalOutstorageNum(yingAnalysisDatav3003.getTotalOutstorageNum());
            setTotalOutstorageMoney(yingAnalysisDatav3003.getTotalOutstorageMoney());
            setTotalStockNum(yingAnalysisDatav3005.getTotalStockNum());
            setTotalStockMoney(yingAnalysisDatav3006.getTotalStockMoney());
            setFinalSettleAmount(yingAnalysisDatav1041.getFinalSettleAmount());
            setUnsettlerBuyerMoneyAmount(yingAnalysisDatav1041.getUnsettlerBuyerMoneyAmount());
            setUnsettlerBuyerNumber(yingAnalysisDatav1041.getUnsettlerBuyerNumber());
            setTradingCompanyAddMoney(yingAnalysisDatav1041.getTradingCompanyAddMoney());
            setSaleCargoAmountofMoney(yingAnalysisDatav1041.getSaleCargoAmountofMoney());
            setCcsProfile(yingAnalysisDatav1045.getCcsProfile());
            setPurchaseCargoAmountOfMoney(yingAnalysisDatav1046.getPurchaseCargoAmountOfMoney());
            setExternalCapitalPaymentAmount(yingAnalysisDatav1047.getExternalCapitalPaymentAmount());
            setOwnerCapitalPaymentAmount(yingAnalysisDatav1048.getOwnerCapitalPaymentAmount());
            setUpstreamCapitalPressure(yingAnalysisDatav1049.getUpstreamCapitalPressure());
            setDownstreamCapitalPressure(yingAnalysisDatav1050.getDownstreamCapitalPressure());
            setCssUninTypeNum(yingAnalysisDatav1051.getCssUninTypeNum());
            setCssUninTypeMoney(yingAnalysisDatav1052.getCssUninTypeMoney());
            setUnInvoicedAmountofMoney(yingAnalysisDatav1052.getUnInvoicedAmountofMoney());
            setCangPrePayment(yingAnalysisDatav1054.getCangPrePayment());
            setSettleGrossProfileNum(yingAnalysisDatav3003.getTotalOutstorageNum());
            setPurchaseIncludeTaxTotalAmount(yingAnalysisDatav1046.getPurchaseCargoAmountOfMoney());

            setSaleIncludeTaxTotalAmount(yingAnalysisDatav1057.getSaleIncludeTaxTotalAmount());
            setTradeCompanyAddMoney(yingAnalysisDatav1058.getTradeCompanyAddMoney());
            setWithoutTaxIncome(yingAnalysisDatav1059.getWithoutTaxIncome());
            setWithoutTaxCost(yingAnalysisDatav1060.getWithoutTaxCost());
            setVat(yingAnalysisDatav1061.getVat());
            setAdditionalTax(yingAnalysisDatav1061.getAdditionalTax());
            setStampDuty(yingAnalysisDatav1063.getStampDuty());
            setOpreationCrossProfile(opreationCrocsProfile);
            setCrossProfileATon((yingAnalysisDatav3003.getTotalOutstorageNum().compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal("0.00") : opreationCrocsProfile.divide(yingAnalysisDatav3003.getTotalOutstorageNum(), 2, BigDecimal.ROUND_HALF_UP)));
            setOwnerCapitalPressure((yingAnalysisDatav3003.getTotalOutstorageNum().compareTo(new BigDecimal("0.00")) == 0
                    ? new BigDecimal("0.00") : yingAnalysisDatav1066.getOwnerCapitalPressure()));
            setSettledDownstreamHuikuanMoneny(yingAnalysisDatav2010.getSettledDownstreamHuikuanMoneny());

            setMaximumPaymentAmount(yingAnalysisDos1.getMaximumPaymentAmount());
            setUnitTotalPaymentAmount(yingAnalysisDatav1001.getTotalPaymentAmount().add(yingAnalysisDatav1007.getTotalRepaymentInterest()));
//            业务累计付款金额
            setAccumulativePaymentAmount(yingAnalysisDos3.getAccumulativePaymentAmount());


            //借款需要字段
            setNonRepaymentLoanMoney(nonRepaymentLoanMoney);


        }};


        return yingAnalysisData;
    }


    public List<AnalysisData> findCangList(Long morderId) {

        List<OrderConfig> orderConfigs = orderConfigService.getList(morderId);
        List<AnalysisData> cangAnalysisDataList = new ArrayList<AnalysisData>();
        for (OrderConfig config : orderConfigs) {
            cangAnalysisDataList.add(this.findOneYing(morderId, config.getId()));
        }
        return cangAnalysisDataList;
    }

    public List<AnalysisData> findYingPartsOneList(Long morderId) {
//        List<AnalysisData> yingAnalysisDataList = yingAnalysisDataMapper.findYingPartsOneList(morderId);
        List<AnalysisData> yingAnalysisPartTwoList = yingAnalysisDataMapper.findYingPartsTwoList(morderId);
        return yingAnalysisPartTwoList;
    }


    public BigDecimal findPurchaseCargoAmountOfMoney(Long orderId, Long hsId, BusinessType businessType) {
        if (businessType.equals(BusinessType.ying)) {
            return yingAnalysisDataMapper.findOneV1046ying(orderId, hsId).getPurchaseCargoAmountOfMoney();
        } else if (businessType.equals(BusinessType.cang)) {
            return yingAnalysisDataMapper.findOneV1046ying(orderId, hsId).getPurchaseCargoAmountOfMoney();
        }

        return new BigDecimal("-1");
    }

    public BigDecimal findTotalPaymentAmount(Long orderId, Long hsId) {

            return yingAnalysisDataMapper.findOneV1001(orderId, hsId).getTotalPaymentAmount();
    }


    public BigDecimal findTotalHuikuanPaymentMoney(Long orderId, Long hsId) {

        return yingAnalysisDataMapper.findOneV1013(orderId, hsId).getTotalHuikuanPaymentMoney();
    }

    public BigDecimal findTotalTradeGapFee(Long orderId, Long hsId) {

        return yingAnalysisDataMapper.findOneV1001(orderId, hsId).getTotalTradeGapFee();
    }
}
