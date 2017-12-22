package com.yimei.hs.same.service;

import com.yimei.hs.cang.entity.CangAnalysisData;
import com.yimei.hs.cang.mapper.CangAnalysisDataMapper;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.PayMode;
import com.yimei.hs.same.dto.PageHuankuanDTO;
import com.yimei.hs.same.dto.PageHuikuanDTO;
import com.yimei.hs.same.entity.*;
import com.yimei.hs.same.mapper.FukuanMapper;
import com.yimei.hs.same.mapper.HuankuanMapper;
import com.yimei.hs.same.mapper.HuikuanMapper;
import com.yimei.hs.ying.entity.AnalysisData;
import com.yimei.hs.ying.mapper.YingAnalysisDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
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
        AnalysisData yingAnalysisDatav1079 = yingAnalysisDataMapper.findOneV1079ying(morderId, hsId);
        AnalysisData yingAnalysisDatav2010 = yingAnalysisDataMapper.findOneV2010ying(morderId, hsId);
        AnalysisData yingAnalysisDos1 = yingAnalysisDataMapper.findOneShowDos1ying(morderId, hsId);
        AnalysisData yingAnalysisDos2 = yingAnalysisDataMapper.findOneShowDos2(morderId, hsId);
        AnalysisData yingAnalysisDos3 = yingAnalysisDataMapper.findOneShowDos3(morderId);
//【1059】不含税收入 - 【1060】不含税成本 - 【1062】税金及附加 - 【1063】印花税 - （【1028】含税汽运费 + 【1029】含税水运费 + 【1030】含税火运费）／1.11 - 【1031】监管费 ／1.06 - 【1031】服务费 ／1.06 - 【1033】业务费
        BigDecimal opreationCrocsProfile = yingAnalysisDatav1059.getWithoutTaxIncome().
                subtract(yingAnalysisDatav1060.getWithoutTaxCost()).
                subtract(yingAnalysisDatav1061.getAdditionalTax()).
                subtract(yingAnalysisDatav1063.getStampDuty()).
                subtract(yingAnalysisDatav1027.getBusinessFee()).
                subtract((yingAnalysisDatav1027.getHsqyFee().add(yingAnalysisDatav1027.getHssyFee()).add(yingAnalysisDatav1027.getHshyFee())).divide(new BigDecimal("1.11"), 2, BigDecimal.ROUND_HALF_UP)).
                subtract((yingAnalysisDatav1027.getSuperviseFee().add(yingAnalysisDatav1027.getServiceFee())).divide(new BigDecimal("1.06"), 2, BigDecimal.ROUND_HALF_UP));

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

        //参与商占压表
        List<CapitalPressure> capitalPressureList = this.utils(morderId, hsId);

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
            setSaleCargoAmountOfMoney(yingAnalysisDatav1041.getSaleCargoAmountOfMoney());
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
            setSaleIncludeTaxTotalAmount(yingAnalysisDatav1041.getSaleCargoAmountOfMoney());
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
            setAccumulativePaymentAmount((yingAnalysisDos3 == null ? BigDecimal.ZERO : yingAnalysisDos3.getAccumulativePaymentAmount()));
            setAmountCargoOfThisTime(yingAnalysisDos2.getAmountCargoOfThisTime());


            //借款需要字段
            setNonRepaymentLoanMoney(nonRepaymentLoanMoney);
//            setFeeAndAddTax(yingAnalysisDatav1027);

            setHsqyCrossProfile(yingAnalysisDatav1027.getHsqyFee());
            setHssyCrossProfile(yingAnalysisDatav1027.getHssyFee());
            setHshyCrossProfile(yingAnalysisDatav1027.getHshyFee());

            setSuperviseCrossProfile(yingAnalysisDatav1027.getSuperviseFee());
            setServiceCrossProfile(yingAnalysisDatav1027.getServiceFee());

            setCapitalPressureList(capitalPressureList);


            setUpstreamUninvoiceAmount(yingAnalysisDatav1079.getUpstreamUninvoiceAmount());
            setUpstreamUninvoicePrice(yingAnalysisDatav1079.getUpstreamUninvoicePrice());
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


        AnalysisData cangAnalysisDatav1001 = yingAnalysisDataMapper.findOneV1001(morderId, hsId);
        AnalysisData cangAnalysisDatav1004 = yingAnalysisDataMapper.findOneV1004(morderId, hsId);
        AnalysisData cangAnalysisDatav1006 = yingAnalysisDataMapper.findOneV1006(morderId, hsId);
        AnalysisData cangAnalysisDatav1007 = yingAnalysisDataMapper.findOneV1007(morderId, hsId);
        AnalysisData cangAnalysisDatav1009 = yingAnalysisDataMapper.findOneV1009(morderId, hsId);
        AnalysisData cangAnalysisDatav1012 = yingAnalysisDataMapper.findOneV1012(morderId, hsId);
        AnalysisData cangAnalysisDatav1013 = yingAnalysisDataMapper.findOneV1013(morderId, hsId);
        AnalysisData cangAnalysisDatav1014 = yingAnalysisDataMapper.findOneV1014(morderId, hsId);
        AnalysisData cangAnalysisDatav1015 = yingAnalysisDataMapper.findOneV1015(morderId, hsId);
        AnalysisData cangAnalysisDatav1016 = yingAnalysisDataMapper.findOneV1016(morderId, hsId);
        AnalysisData cangAnalysisDatav1017 = yingAnalysisDataMapper.findOneV1017(morderId, hsId);
        AnalysisData cangAnalysisDatav1018 = yingAnalysisDataMapper.findOneV1018(morderId, hsId);
//        AnalysisData yingAnalysisDatav1019 = yingAnalysisDataMapper.findOneV1019(morderId, hsId);
        AnalysisData cangAnalysisDatav1020 = yingAnalysisDataMapper.findOneV1020(morderId, hsId);
        AnalysisData cangAnalysisDatav1021 = yingAnalysisDataMapper.findOneV1021(morderId, hsId);
        AnalysisData cangAnalysisDatav1023 = yingAnalysisDataMapper.findOneV1023(morderId, hsId);
        AnalysisData cangAnalysisDatav1024 = yingAnalysisDataMapper.findOneV1024(morderId, hsId);
        AnalysisData cangAnalysisDatav1027 = yingAnalysisDataMapper.findOneV1027(morderId, hsId);
        AnalysisData cangAnalysisDatav1035 = yingAnalysisDataMapper.findOneV1035(morderId, hsId);
        AnalysisData cangAnalysisDatav1037 = yingAnalysisDataMapper.findOneV1037(morderId, hsId);
        AnalysisData cangAnalysisDatav1039 = yingAnalysisDataMapper.findOneV1039(morderId, hsId);
        AnalysisData cangAnalysisDatav3001 = yingAnalysisDataMapper.findOneV3001(morderId, hsId);
        AnalysisData cangAnalysisDatav3003 = yingAnalysisDataMapper.findOneV3003(morderId, hsId);
        AnalysisData cangAnalysisDatav3005 = yingAnalysisDataMapper.findOneV3005(morderId, hsId);
        AnalysisData cangAnalysisDatav3006 = yingAnalysisDataMapper.findOneV3006(morderId, hsId);
        AnalysisData cangAnalysisDatav3010 = yingAnalysisDataMapper.findOneV3010(morderId, hsId);
        AnalysisData cangAnalysisDatav3013 = yingAnalysisDataMapper.findOneV3013(morderId, hsId);
        AnalysisData cangAnalysisDatav3014 = yingAnalysisDataMapper.findOneV3014(morderId, hsId);

        AnalysisData cangAnalysisDatav1041 = yingAnalysisDataMapper.findOneV1040cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1044 = yingAnalysisDataMapper.findOneV1044cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1045 = yingAnalysisDataMapper.findOneV1045(morderId, hsId);
        AnalysisData cangAnalysisDatav1046 = yingAnalysisDataMapper.findOneV1046cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1047 = yingAnalysisDataMapper.findOneV1047(morderId, hsId);
        AnalysisData cangAnalysisDatav1048 = yingAnalysisDataMapper.findOneV1048cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1049 = yingAnalysisDataMapper.findOneV1049cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1050 = yingAnalysisDataMapper.findOneV1050cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1051 = yingAnalysisDataMapper.findOneV1051cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1052 = yingAnalysisDataMapper.findOneV1052cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1054 = yingAnalysisDataMapper.findOneV1054cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1056 = yingAnalysisDataMapper.findOneV1056cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1057 = yingAnalysisDataMapper.findOneV1057cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1058 = yingAnalysisDataMapper.findOneV1058cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1059 = yingAnalysisDataMapper.findOneV1059cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1060 = yingAnalysisDataMapper.findOneV1060cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1061 = yingAnalysisDataMapper.findOneV1061cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1063 = yingAnalysisDataMapper.findOneV1063cang(morderId, hsId);


        AnalysisData cangAnalysisDatav1066 = yingAnalysisDataMapper.findOneV1066cang(morderId, hsId);
        AnalysisData cangAnalysisDatav2010 = yingAnalysisDataMapper.findOneV2010cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1078 = yingAnalysisDataMapper.findOneV1078cang(morderId, hsId);
        AnalysisData cangAnalysisDatav1079 = yingAnalysisDataMapper.findOneV1079cang(morderId, hsId);


        AnalysisData cangAnalysisDos1 = yingAnalysisDataMapper.findOneShowDos1cang(morderId, hsId);
        AnalysisData cangAnalysisDos3 = yingAnalysisDataMapper.findOneShowDos3(morderId);


        //参与方占压表
        List<CapitalPressure> capitalPressureList = this.utils(morderId, hsId);
//        【1059】不含税收入 - 【1060】不含税成本 - 【1062】税金及附加 - 【1063】印花税 -
        // （【1028】含税汽运费 + 【1029】含税水运费 + 【1030】含税火运费）／1.11 - 【1031】监管费 ／1.06 - 【1031】服务费 ／1.06
// - 【1033】业务费


//【1059】不含税收入 - 【1060】不含税成本 - 【1062】税金及附加 - 【1063】印花税 -
//（【1069】毛利含税汽运费 + 【1070】毛利含税水运费 + 【1071】毛利含税火运费）／1.11 - 【1072】毛利监管费 ／1.06 - 【1073】毛利服务费 ／1.06 - 【1074】



        BigDecimal opreationCrocsProfile = cangAnalysisDatav1059.getWithoutTaxIncome().
                subtract(cangAnalysisDatav1060.getWithoutTaxCost()).
                subtract(cangAnalysisDatav1061.getAdditionalTax()).
                subtract(cangAnalysisDatav1063.getStampDuty()).
                subtract(cangAnalysisDatav1027.getBusinessFee().multiply(cangAnalysisDatav3005.getInOutRate())).

                subtract(
                        (
                                cangAnalysisDatav3005.getInOutRate().multiply(cangAnalysisDatav1027.getHsqyFee().
                                        add(cangAnalysisDatav1027.getHssyFee()).
                                        add(cangAnalysisDatav1027.getHshyFee()))
                        ).divide(new BigDecimal("1.11"), 2, BigDecimal.ROUND_HALF_UP)
                ).
                subtract(cangAnalysisDatav1027.getSuperviseFee().multiply(cangAnalysisDatav3005.getInOutRate()).divide(new BigDecimal("1.06"), 2, BigDecimal.ROUND_HALF_UP)).
                subtract(cangAnalysisDatav1027.getServiceFee().multiply(cangAnalysisDatav3005.getInOutRate()).divide(new BigDecimal("1.06"), 2, BigDecimal.ROUND_HALF_UP));


        AnalysisData cangAnalysisData = new AnalysisData() {{
            setOrderId(orderConfigBase.getOrderId());
            setHsId(orderConfigBase.getHsId());
            setHsMonth(orderConfigBase.getHsMonth());
            setTotalPayTrafficFee(cangAnalysisDatav1001.getTotalPayTrafficFee());
            setTotalTradeGapFee(cangAnalysisDatav1001.getTotalTradeGapFee());
            setTotalPaymentAmount(cangAnalysisDatav1001.getTotalPaymentAmount());
            setTotalLoadMoney(cangAnalysisDatav1004.getTotalLoadMoney());
            setTotalUnrepaymentEstimateCost((cangAnalysisDatav1006 == null ? new BigDecimal("0.00") : cangAnalysisDatav1006.getTotalUnrepaymentEstimateCost()));
            setTotalRepaymentPrincipeAmount(cangAnalysisDatav1007.getTotalRepaymentPrincipeAmount());
            setTotalRepaymentPrincipeAmount(cangAnalysisDatav1007.getTotalRepaymentPrincipeAmount());
            setTotalRepaymentInterest(cangAnalysisDatav1007.getTotalRepaymentInterest());
            setTotalServiceCharge(cangAnalysisDatav1007.getTotalServiceCharge());
            setTotalUnpayPrincipal((cangAnalysisDatav1009 == null ? new BigDecimal("0.00") : cangAnalysisDatav1009.getTotalUnpayPrincipal()));
            setTotalUnpayInterest((cangAnalysisDatav1009 == null ? new BigDecimal("0.00") : cangAnalysisDatav1009.getTotalUnpayInterest()));
            setTotalUnpayFee((cangAnalysisDatav1009 == null ? new BigDecimal("0.00") : cangAnalysisDatav1009.getTotalUnpayFee()));
            setOutCapitalAmout(cangAnalysisDatav1012.getOutCapitalAmout());
            setTotalHuikuanPaymentMoney(cangAnalysisDatav1013.getTotalHuikuanPaymentMoney());
            setPayCargoAmount(cangAnalysisDatav1014.getPayCargoAmount());
            setUnpaymentMoney(cangAnalysisDatav1015.getUnpaymentMoney());
            setUnpaymentEstimateProfile(cangAnalysisDatav1016.getUnpaymentEstimateProfile());
            setInterestDays(cangAnalysisDatav1017.getInterestDays());
            setActualUtilizationRate(cangAnalysisDatav1018.getActualUtilizationRate());
//            setRate(cangAnalysisDatav1019.getRate());
            setTotalPaymentedRateMoney(cangAnalysisDatav1020.getTotalPaymentedRateMoney());
            setContractRateProfile(cangAnalysisDatav1021.getContractRateProfile());
            setTiexianRateAmount(cangAnalysisDatav1023.getTiexianRateAmount());
            setTotalBuyerMoney(cangAnalysisDatav1024.getTotalBuyerMoney());
            setTotalBuyerNums(cangAnalysisDatav1024.getTotalBuyerNums());
            setTotalBuyersettleGap(cangAnalysisDatav1024.getTotalBuyersettleGap());
            setDsddFee(cangAnalysisDatav1027.getDsddFee());
            setHshyFee(cangAnalysisDatav1027.getHshyFee());
            setHsqyFee(cangAnalysisDatav1027.getHsqyFee());
            setHssyFee(cangAnalysisDatav1027.getHssyFee());
            setBusinessFee(cangAnalysisDatav1027.getBusinessFee());
            setServiceFee(cangAnalysisDatav1027.getServiceFee());
            setSuperviseFee(cangAnalysisDatav1027.getSuperviseFee());
            setSalesFeeAmount(cangAnalysisDatav1027.getSalesFeeAmount());
            setTradingCompanyInTpeMoneyAmount((cangAnalysisDatav1035 == null ? new BigDecimal("0.00") : cangAnalysisDatav1035.getTradingCompanyInTpeMoneyAmount()));
            setTradingCompanyInTypeNum((cangAnalysisDatav1035 == null ? new BigDecimal("0.00") : cangAnalysisDatav1035.getTradingCompanyInTypeNum()));
            setTotalCCSIntypeMoney((cangAnalysisDatav1037 == null ? new BigDecimal("0.00") : cangAnalysisDatav1037.getTotalCCSIntypeMoney()));
            setTotalCSSIntypeNumber((cangAnalysisDatav1037 == null ? new BigDecimal("0.00") : cangAnalysisDatav1037.getTotalCSSIntypeNumber()));
            setInvoicedMoneyAmount((cangAnalysisDatav1039 == null ? new BigDecimal("0.00") : cangAnalysisDatav1039.getInvoicedMoneyAmount()));
            setInvoicedMoneyNum((cangAnalysisDatav1039 == null ? new BigDecimal("0.00") : cangAnalysisDatav1039.getInvoicedMoneyNum()));
            setTotalInstorageAmount(cangAnalysisDatav3001.getTotalInstorageAmount());
            setTotalInstorageNum(cangAnalysisDatav3001.getTotalInstorageNum());
            setInstorageUnitPrice(cangAnalysisDatav3001.getInstorageUnitPrice());
            setTotalOutstorageNum(cangAnalysisDatav3003.getTotalOutstorageNum());


            setTotalOutstorageMoney(cangAnalysisDatav3003.getTotalOutstorageMoney());
            setTotalStockNum(cangAnalysisDatav3005.getTotalStockNum());
            setTotalStockMoney(cangAnalysisDatav3006.getTotalStockMoney());
            setFinalSettleAmount(cangAnalysisDatav1041.getFinalSettleAmount());
            setUnsettlerBuyerMoneyAmount(cangAnalysisDatav1041.getUnsettlerBuyerMoneyAmount());
            setUnsettlerBuyerNumber(cangAnalysisDatav1041.getUnsettlerBuyerNumber());
            setTradingCompanyAddMoney(cangAnalysisDatav1041.getTradingCompanyAddMoney());
            setSaleCargoAmountOfMoney(cangAnalysisDatav1044.getSaleCargoAmountOfMoney());
            setCcsProfile(cangAnalysisDatav1045.getCcsProfile());
            setPurchaseCargoAmountOfMoney(cangAnalysisDatav1046.getPurchaseCargoAmountOfMoney());
            setExternalCapitalPaymentAmount(cangAnalysisDatav1047.getExternalCapitalPaymentAmount());
            setOwnerCapitalPaymentAmount(cangAnalysisDatav1048.getOwnerCapitalPaymentAmount());
            setUpstreamCapitalPressure(cangAnalysisDatav1049.getUpstreamCapitalPressure());
            setDownstreamCapitalPressure(cangAnalysisDatav1050.getDownstreamCapitalPressure());
            setCssUninTypeNum(cangAnalysisDatav1051.getCssUninTypeNum());
            setCssUninTypeMoney(cangAnalysisDatav1052.getCssUninTypeMoney());
            setUnInvoicedAmountofMoney(cangAnalysisDatav1052.getUnInvoicedAmountofMoney());
            setCangPrePayment(cangAnalysisDatav1054.getCangPrePayment());
            setSettleGrossProfileNum(cangAnalysisDatav3003.getTotalOutstorageNum());
            setPurchaseIncludeTaxTotalAmount(cangAnalysisDatav1056.getPurchaseIncludeTaxTotalAmount());

            setSaleIncludeTaxTotalAmount(cangAnalysisDatav1057.getSaleIncludeTaxTotalAmount());
            setTradeCompanyAddMoney(cangAnalysisDatav1058.getTradeCompanyAddMoney());
            setWithoutTaxIncome(cangAnalysisDatav1059.getWithoutTaxIncome());
            setWithoutTaxCost(cangAnalysisDatav1060.getWithoutTaxCost());
            setVat(cangAnalysisDatav1061.getVat());
            setAdditionalTax(cangAnalysisDatav1061.getAdditionalTax());
            setStampDuty(cangAnalysisDatav1063.getStampDuty());
            setOpreationCrossProfile(opreationCrocsProfile);
            setCrossProfileATon((cangAnalysisDatav3003.getTotalOutstorageNum().compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal("0.00") : opreationCrocsProfile.divide(cangAnalysisDatav3003.getTotalOutstorageNum(), 2, BigDecimal.ROUND_HALF_UP)));
            setOwnerCapitalPressure(cangAnalysisDatav1066.getOwnerCapitalPressure());
            setSettledDownstreamHuikuanMoneny(cangAnalysisDatav2010.getSettledDownstreamHuikuanMoneny());

            setMaximumPaymentAmount(cangAnalysisDos1.getMaximumPaymentAmount());
            setUnitTotalPaymentAmount(cangAnalysisDatav1001.getTotalPaymentAmount().add(cangAnalysisDatav1007.getTotalRepaymentInterest()));
//            业务累计付款金额
            setAccumulativePaymentAmount(cangAnalysisDos3.getAccumulativePaymentAmount());


            //3009
            setTotalInstorageTranitNum(cangAnalysisDatav3001.getTotalInstorageTranitNum());
            //在途中剩余金额  3010
            setTotalInstorageTranitPrice(cangAnalysisDatav3010.getTotalInstorageTranitPrice());
            //3011
            setTotalInstoragedNum(cangAnalysisDatav3001.getTotalInstoragedNum());

            // 12剩余库存数量   【3011】已入库库存数量-【3003】已出库数量 2012
            BigDecimal remaindInstorageNumber = cangAnalysisDatav3001.getTotalInstoragedNum().subtract(cangAnalysisDatav3003.getTotalOutstorageNum());

            setTotalInstorageRemainNum(remaindInstorageNumber);
            //13 剩余库存金额=剩余库存数量*单价 3013

            setTotalInstorageRemainPrice(cangAnalysisDatav3013.getTotalInstorageRemainPrice());


            BigDecimal inOutRate = cangAnalysisDatav3005.getInOutRate();
            setHsqyCrossProfile(cangAnalysisDatav1027.getHsqyFee().multiply(inOutRate));
            setHssyCrossProfile(cangAnalysisDatav1027.getHssyFee().multiply(inOutRate));
            setHshyCrossProfile(cangAnalysisDatav1027.getHshyFee().multiply(inOutRate));
            setSuperviseCrossProfile(cangAnalysisDatav1027.getSuperviseFee().multiply(inOutRate));
            setServiceCrossProfile(cangAnalysisDatav1027.getServiceFee().multiply(inOutRate));
            setInOutRate(inOutRate);
//
//            //合计数量
//            setTotalSumInstorageNum(remaindInstorageNumber.add(cangAnalysisDatav3001.getTotalInstorageTranitNum()));
//            //合计金额
//            setTotalSumInstoragePrice(totalInstorageRemainPrice.add(cangAnalysisDatav3001.getTotalInstorageTranitPrice()));
//


            //借款需要字段
            setNonRepaymentLoanMoney(nonRepaymentLoanMoney);

            setCapitalPressureList(capitalPressureList);
            setWithoutTaxFee(cangAnalysisDatav3014.getWithoutTaxFee());

            //

            setUnsettleSellerMoneyAmount(cangAnalysisDatav1078.getUnsettleSellerMoneyAmount());

            setUpstreamUninvoiceAmount(cangAnalysisDatav1079.getUpstreamUninvoiceAmount());
            setUpstreamUninvoicePrice(cangAnalysisDatav1079.getUpstreamUninvoicePrice());

        }};


        return cangAnalysisData;
    }


    public List<AnalysisData> findCangList(Long morderId) {

        List<OrderConfig> orderConfigs = orderConfigService.getList(morderId);
        List<AnalysisData> cangAnalysisDataList = new ArrayList<AnalysisData>();
        for (OrderConfig config : orderConfigs) {
            cangAnalysisDataList.add(this.findOneCang(config.getId(), morderId));

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

    public AnalysisData findV1001(Long morderId, Long hsId) {
        return yingAnalysisDataMapper.findOneV1001(morderId, hsId);
    }

    public AnalysisData findV3001(Long morderId, long hsId) {
        return yingAnalysisDataMapper.findOneV3001(morderId, hsId);
    }

    public AnalysisData findV3003(Long morderId, long hsId) {
        return yingAnalysisDataMapper.findOneV3003(morderId, hsId);
    }

    public OrderConfig findBase(Long morderId, long hsId) {
        return yingAnalysisDataMapper.findOneVBase(morderId, hsId);
    }

    public BigDecimal findHuikuanExceptBail(Long morderId, long hsId) {
        return yingAnalysisDataMapper.findHuikuanExceptBail(morderId, hsId);
    }

    public List<CapitalPressure> utils(Long orderId, Long hsId) {

        List<Long> partiesId = yingAnalysisDataMapper.selectPartyId(orderId, hsId);
        if (partiesId == null || partiesId.size() == 0) {
            return null;
        }
        List<CapitalPressure> capitalPressureList = new ArrayList<CapitalPressure>();

        for (Long id : partiesId) {

            CapitalPressure capitalPressure = new CapitalPressure();
            BigDecimal capitalFee = yingAnalysisDataMapper.findOneV1075Fee(id, orderId, hsId);
            BigDecimal capitalFukuan = yingAnalysisDataMapper.findOneV1075Fukuan(id, orderId, hsId);
            BigDecimal unInvoice = yingAnalysisDataMapper.findOneV1076Uninvoice(id, orderId, hsId);
            capitalPressure.setHsId(hsId);
            capitalPressure.setReceiveCompanyId(id);
            capitalPressure.setOrderId(orderId);
            capitalPressure.setUnInvoicePrice(
                                    (capitalFee == null ||
                                            capitalFee.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : capitalFee).subtract( (unInvoice == null
                                            || unInvoice.compareTo(BigDecimal.ZERO)
                                            == 0 ? BigDecimal.ZERO : unInvoice)));
            capitalPressure.setPartiesCapitalPressure((capitalFukuan == null || capitalFukuan.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : capitalFukuan).subtract((capitalFee == null || capitalFee.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : capitalFee)));
            capitalPressureList.add(capitalPressure);
        }
        return capitalPressureList;
    }

    //  付款列表
    @Autowired
    FukuanMapper fukuanMapper;
    //回款付款列表

    //还款
    @Autowired
    HuankuanService huankuanMapper;
    //回款
    @Autowired
    HuikuanMapper huikuanMapper;


    public List<ExportExcelDate> exportExcel(Long orderId,Long hsId) {

       List<Fukuan>  fukuanList=fukuanMapper.getListByOrderIdAndHsId(orderId, hsId);
       List<AnalysisData> analysisDatas = yingAnalysisDataMapper.findOneV1019(orderId, hsId);


        List<Huikuan> huikuanList = huikuanMapper.gelistByhsIdAndOrderId(orderId, hsId);

        PageHuankuanDTO pageHuankuanDTO = new PageHuankuanDTO();
        pageHuankuanDTO.setPageNo(1);
        pageHuankuanDTO.setPageSize(10000);
        pageHuankuanDTO.setOrderId(orderId);
        pageHuankuanDTO.setHsId(hsId);

        List<Huankuan> huankuans = huankuanMapper.getPage(pageHuankuanDTO).getResults();

        List<ExportExcelDate> exportExcelDates = new ArrayList<ExportExcelDate>();

        if (analysisDatas!=null) {

            int analysisiDataSize = analysisDatas.size();
            int fukuanListSize = fukuanList.size();
            int huikuanListSize = huikuanList.size();
            int huankuansSize = huankuans.size();
            List<Integer> listSize = new ArrayList<Integer>();

            listSize.add(analysisiDataSize);
            listSize.add(fukuanListSize);
            listSize.add(huikuanListSize);
            listSize.add(huankuansSize);
            int maxSize = listSize.stream().mapToInt((x) -> x).max().getAsInt();

            for (int i=0;i<maxSize;i++) {
                ExportExcelDate exportExcelDate = new ExportExcelDate();

                if (i <fukuanListSize) {
                    exportExcelDate.setFukuanDate(fukuanList.get(i).getPayDate());
                    exportExcelDate.setFukuanAmount(fukuanList.get(i).getPayAmount());
                }

                if (i <analysisiDataSize) {

                    exportExcelDate.setAmount(analysisDatas.get(i).getMapAmount());
                    exportExcelDate.setPayDate(analysisDatas.get(i).getPayDate());
                    exportExcelDate.setHuikuanDate(analysisDatas.get(i).getHuikuanDate());
                    exportExcelDate.setTime(analysisDatas.get(i).getTime());
                    exportExcelDate.setRate(analysisDatas.get(i).getRate());
                }
                if (i<huikuanListSize) {

                    Huikuan huikuan=huikuanList.get(i);
                    exportExcelDate.setHuikuanTime(huikuan.getHuikuanDate());
                    exportExcelDate.setHuikuanAmount(huikuan.getHuikuanAmount());
                    exportExcelDate.setHuikuanMode(huikuan.getHuikuanMode());

                    if (huikuan.getHuikuanMode().equals(PayMode.BANK_ACCEPTANCE)) {
                        Long durTime = Duration.between(huikuan.getHuikuanBusinessPaperExpire(), huikuan.getHuikuanBankPaperDate()).toDays();
                        BigDecimal rate = huikuan.getHuikuanBankDiscountRate().multiply(new BigDecimal(durTime)).multiply(huikuan.getHuikuanAmount()).multiply(new BigDecimal("1.17")).divide(new BigDecimal("360"),2,BigDecimal.ROUND_UP);
                        exportExcelDate.setTieRate(rate);
                    }
                }


                if (i<huankuansSize) {
                    Huankuan huikuan = huankuans.get(i);
                    if (huikuan.getHuankuanMapList() == null) {
                        exportExcelDate.setHuankuanInterest(BigDecimal.ZERO);
                    } else {
                        exportExcelDate.setHuankuanInterest(huikuan.getHuankuanMapList().stream().map(m -> m.getInterest()).reduce(BigDecimal.ZERO, BigDecimal::add));
                    }
                    if (huikuan.getHuankuanMapList() == null) {
                        exportExcelDate.setHuankuanServiceFee(BigDecimal.ZERO);
                    } else {
                        exportExcelDate.setHuankuanServiceFee(huikuan.getHuankuanMapList().stream().map(m -> m.getFee()).reduce(BigDecimal.ZERO, BigDecimal::add));
                    }
                    exportExcelDate.setCcs(huikuan.getPromise());
                }

                exportExcelDates.add(exportExcelDate);

            }

        }else {
            return null;
        }
            return exportExcelDates;

    }

}
