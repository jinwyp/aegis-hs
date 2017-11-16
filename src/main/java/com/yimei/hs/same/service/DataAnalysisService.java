package com.yimei.hs.same.service;

import com.yimei.hs.cang.entity.CangAnalysisData;
import com.yimei.hs.cang.mapper.CangAnalysisDataMapper;
import com.yimei.hs.ying.entity.YingAnalysisData;
import com.yimei.hs.ying.mapper.YingAnalysisDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class DataAnalysisService {

    @Autowired
    YingAnalysisDataMapper yingAnalysisDataMapper;

    @Autowired
    CangAnalysisDataMapper cangAnalysisDataMapper;


    public YingAnalysisData findOneYing(Long morderId, long hsId) {
        YingAnalysisData yingAnalysisDatav1001 = yingAnalysisDataMapper.findOneV1001(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1004 = yingAnalysisDataMapper.findOneV1004(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1006 = yingAnalysisDataMapper.findOneV1006(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1007 = yingAnalysisDataMapper.findOneV1007(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1009 = yingAnalysisDataMapper.findOneV1009(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1012 = yingAnalysisDataMapper.findOneV1012(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1013 = yingAnalysisDataMapper.findOneV1013(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1014 = yingAnalysisDataMapper.findOneV1014(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1015 = yingAnalysisDataMapper.findOneV1015(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1016 = yingAnalysisDataMapper.findOneV1016(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1017 = yingAnalysisDataMapper.findOneV1017(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1018 = yingAnalysisDataMapper.findOneV1018(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1019 = yingAnalysisDataMapper.findOneV1019(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1020 = yingAnalysisDataMapper.findOneV1020(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1021 = yingAnalysisDataMapper.findOneV1021(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1023 = yingAnalysisDataMapper.findOneV1023(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1024 = yingAnalysisDataMapper.findOneV1024(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1027 = yingAnalysisDataMapper.findOneV1027(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1035 = yingAnalysisDataMapper.findOneV1035(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1037 = yingAnalysisDataMapper.findOneV1037(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1039 = yingAnalysisDataMapper.findOneV1039(morderId, hsId);
        YingAnalysisData yingAnalysisDatav2001 = yingAnalysisDataMapper.findOneV2001(morderId, hsId);
        YingAnalysisData yingAnalysisDatav2004 = yingAnalysisDataMapper.findOneV2004(morderId, hsId);
        YingAnalysisData yingAnalysisDatav2008 = yingAnalysisDataMapper.findOneV2008(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1041 = yingAnalysisDataMapper.findOneV1040ying(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1045= yingAnalysisDataMapper.findOneV1045(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1046 = yingAnalysisDataMapper.findOneV1046ying(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1047 = yingAnalysisDataMapper.findOneV1047(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1048 = yingAnalysisDataMapper.findOneV1048ying(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1049 = yingAnalysisDataMapper.findOneV1049ying(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1051 = yingAnalysisDataMapper.findOneV1051ying(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1052 = yingAnalysisDataMapper.findOneV1052ying(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1054 = yingAnalysisDataMapper.findOneV1054ying(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1055 = yingAnalysisDataMapper.findOneV1055ying(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1058 = yingAnalysisDataMapper.findOneV1058ying(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1059 = yingAnalysisDataMapper.findOneV1059ying(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1060 = yingAnalysisDataMapper.findOneV1060ying(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1061 = yingAnalysisDataMapper.findOneV1061ying(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1063 = yingAnalysisDataMapper.findOneV1063ying(morderId, hsId);
//        YingAnalysisData yingAnalysisDatav1064 = yingAnalysisDataMapper.findOneV1064ying(morderId, hsId);

        YingAnalysisData yingAnalysisDatav1066 = yingAnalysisDataMapper.findOneV1066ying(morderId, hsId);
        YingAnalysisData yingAnalysisDatav2010 = yingAnalysisDataMapper.findOneV2010ying(morderId, hsId);


        YingAnalysisData yingAnalysisData = new YingAnalysisData() {{
            setTotalPayTrafficFee(yingAnalysisDatav1001.getTotalPayTrafficFee());
            setTotalTradeGapFee(yingAnalysisDatav1001.getTotalTradeGapFee());
            setTotalTradeGapFee(yingAnalysisDatav1001.getTotalTradeGapFee());
            setTotalPaymentAmount(yingAnalysisDatav1001.getTotalPaymentAmount());
            setTotalLoadMoney(yingAnalysisDatav1004.getTotalLoadMoney());
            setTotalUnrepaymentEstimateCost(yingAnalysisDatav1006.getTotalUnrepaymentEstimateCost());
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
            setUnpaymentMoney( yingAnalysisDatav1015.getUnpaymentMoney());
            setUnpaymentEstimateProfile(yingAnalysisDatav1016.getUnpaymentEstimateProfile());
            setInterestDays(yingAnalysisDatav1017.getInterestDays());
            setActualUtilizationRate(yingAnalysisDatav1018.getActualUtilizationRate());
            setRate(yingAnalysisDatav1019.getRate());
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
            setTotalCCSIntypeMoney((yingAnalysisDatav1035 == null ? new BigDecimal("0.00") : yingAnalysisDatav1037.getTotalCCSIntypeMoney()));
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
            setCssUninTypeNum(yingAnalysisDatav1051.getCssUninTypeNum());
            setCssUninTypeMoney(yingAnalysisDatav1052.getCssUninTypeMoney());
            setUnInvoicedAmountofMoney(yingAnalysisDatav1052.getUnInvoicedAmountofMoney());
            setYingPrePayment(yingAnalysisDatav1054.getYingPrePayment());
            setSettleGrossProfileNum(yingAnalysisDatav1046.getPurchaseCargoAmountOfMoney());
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
//            setOpreationCrossProfile(yingAnalysisDatav1064.getOpreationCrossProfile());
//            setCrossProfileATon(yingAnalysisDatav1064.getOpreationCrossProfile().subtract(yingAnalysisDatav1055.getSettleGrossProfileNum()));

        }};

        return yingAnalysisData;


    }


    public List<YingAnalysisData> findYingList(Long morderId) {
        List<YingAnalysisData> yingAnalysisDataList = yingAnalysisDataMapper.findList(morderId);
        return yingAnalysisDataList;
    }


    public CangAnalysisData findOneCang(Long hsId, Long morderId) {

        CangAnalysisData cangAnalysisData = cangAnalysisDataMapper.findOne(hsId, morderId);
//
//        return cangAnalysisData;
        YingAnalysisData yingAnalysisDatav1001 = yingAnalysisDataMapper.findOneV1001(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1004 = yingAnalysisDataMapper.findOneV1004(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1006 = yingAnalysisDataMapper.findOneV1006(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1007 = yingAnalysisDataMapper.findOneV1007(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1009 = yingAnalysisDataMapper.findOneV1009(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1012 = yingAnalysisDataMapper.findOneV1012(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1013 = yingAnalysisDataMapper.findOneV1013(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1014 = yingAnalysisDataMapper.findOneV1014(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1015 = yingAnalysisDataMapper.findOneV1015(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1016 = yingAnalysisDataMapper.findOneV1016(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1017 = yingAnalysisDataMapper.findOneV1017(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1018 = yingAnalysisDataMapper.findOneV1018(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1019 = yingAnalysisDataMapper.findOneV1019(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1020 = yingAnalysisDataMapper.findOneV1020(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1021 = yingAnalysisDataMapper.findOneV1021(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1023 = yingAnalysisDataMapper.findOneV1023(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1024 = yingAnalysisDataMapper.findOneV1024(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1027 = yingAnalysisDataMapper.findOneV1027(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1035 = yingAnalysisDataMapper.findOneV1035(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1037 = yingAnalysisDataMapper.findOneV1037(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1039 = yingAnalysisDataMapper.findOneV1039(morderId, hsId);
        YingAnalysisData yingAnalysisDatav3001 = yingAnalysisDataMapper.findOneV3001(morderId, hsId);
        YingAnalysisData yingAnalysisDatav3003 = yingAnalysisDataMapper.findOneV3003(morderId, hsId);
        YingAnalysisData yingAnalysisDatav3005 = yingAnalysisDataMapper.findOneV3005(morderId, hsId);
        YingAnalysisData yingAnalysisDatav3006 = yingAnalysisDataMapper.findOneV3006(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1041 = yingAnalysisDataMapper.findOneV1040cang(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1045= yingAnalysisDataMapper.findOneV1045(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1046 = yingAnalysisDataMapper.findOneV1046cang(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1047 = yingAnalysisDataMapper.findOneV1047(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1048 = yingAnalysisDataMapper.findOneV1048cang(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1049 = yingAnalysisDataMapper.findOneV1049cang(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1051 = yingAnalysisDataMapper.findOneV1051cang(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1052 = yingAnalysisDataMapper.findOneV1052cang(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1054 = yingAnalysisDataMapper.findOneV1054cang(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1056 = yingAnalysisDataMapper.findOneV1056cang(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1057 = yingAnalysisDataMapper.findOneV1057cang(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1058 = yingAnalysisDataMapper.findOneV1058cang(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1059 = yingAnalysisDataMapper.findOneV1059cang(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1060 = yingAnalysisDataMapper.findOneV1060cang(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1061 = yingAnalysisDataMapper.findOneV1061cang(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1063 = yingAnalysisDataMapper.findOneV1063cang(morderId, hsId);
        YingAnalysisData yingAnalysisDatav1064 = yingAnalysisDataMapper.findOneV1064cang(morderId, hsId);

        YingAnalysisData yingAnalysisDatav1066 = yingAnalysisDataMapper.findOneV1066cang(morderId, hsId);
        YingAnalysisData yingAnalysisDatav2010 = yingAnalysisDataMapper.findOneV2010cang(morderId, hsId);


        YingAnalysisData yingAnalysisData = new YingAnalysisData() {{
            setTotalPayTrafficFee(yingAnalysisDatav1001.getTotalPayTrafficFee());
            setTotalTradeGapFee(yingAnalysisDatav1001.getTotalTradeGapFee());
            setTotalTradeGapFee(yingAnalysisDatav1001.getTotalTradeGapFee());
            setTotalPaymentAmount(yingAnalysisDatav1001.getTotalPaymentAmount());
            setTotalLoadMoney(yingAnalysisDatav1004.getTotalLoadMoney());
            setTotalUnrepaymentEstimateCost(yingAnalysisDatav1006.getTotalUnrepaymentEstimateCost());
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
            setUnpaymentMoney( yingAnalysisDatav1015.getUnpaymentMoney());
            setUnpaymentEstimateProfile(yingAnalysisDatav1016.getUnpaymentEstimateProfile());
            setInterestDays(yingAnalysisDatav1017.getInterestDays());
            setActualUtilizationRate(yingAnalysisDatav1018.getActualUtilizationRate());
            setRate(yingAnalysisDatav1019.getRate());
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
            setTotalCCSIntypeMoney((yingAnalysisDatav1035 == null ? new BigDecimal("0.00") : yingAnalysisDatav1037.getTotalCCSIntypeMoney()));
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
            setCssUninTypeNum(yingAnalysisDatav1051.getCssUninTypeNum());
            setCssUninTypeMoney(yingAnalysisDatav1052.getCssUninTypeMoney());
            setUnInvoicedAmountofMoney(yingAnalysisDatav1052.getUnInvoicedAmountofMoney());
            setCangPrePayment(yingAnalysisDatav1054.getCangPrePayment());
            setSettleGrossProfileNum(yingAnalysisDatav3003.getTotalOutstorageNum());
            setPurchaseIncludeTaxTotalAmount(yingAnalysisDatav1056.getPurchaseIncludeTaxTotalAmount());
            setSaleIncludeTaxTotalAmount(yingAnalysisDatav1057.getSaleIncludeTaxTotalAmount());
            setTradeCompanyAddMoney(yingAnalysisDatav1058.getTradeCompanyAddMoney());
            setWithoutTaxIncome(yingAnalysisDatav1059.getWithoutTaxIncome());
            setWithoutTaxCost(yingAnalysisDatav1060.getWithoutTaxCost());
            setVat(yingAnalysisDatav1061.getVat());
            setAdditionalTax(yingAnalysisDatav1061.getAdditionalTax());
            setStampDuty(yingAnalysisDatav1063.getStampDuty());
            setOpreationCrossProfile(yingAnalysisDatav1064.getOpreationCrossProfile());
            setCrossProfileATon(yingAnalysisDatav1064.getOpreationCrossProfile().subtract(yingAnalysisDatav3003.getTotalOutstorageNum()));
            setOwnerCapitalPressure(yingAnalysisDatav1066.getOwnerCapitalPressure());
            setSettledDownstreamHuikuanMoneny(yingAnalysisDatav2010.getSettledDownstreamHuikuanMoneny());



        }};


        return cangAnalysisData;
    }


    public List<CangAnalysisData> findCangList(Long orderId) {

        List<CangAnalysisData> cangAnalysisDataList = cangAnalysisDataMapper.findList(orderId);

        return cangAnalysisDataList;
    }

    public List<YingAnalysisData> findYingPartsOneList(Long morderId) {
//        List<YingAnalysisData> yingAnalysisDataList = yingAnalysisDataMapper.findYingPartsOneList(morderId);
        List<YingAnalysisData> yingAnalysisPartTwoList = yingAnalysisDataMapper.findYingPartsTwoList(morderId);
        return yingAnalysisPartTwoList;
    }
}
