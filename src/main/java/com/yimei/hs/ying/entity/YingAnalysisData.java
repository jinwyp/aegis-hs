package com.yimei.hs.ying.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class YingAnalysisData implements Serializable {

    private Long hsId;

    private Long orderId;

    private BigDecimal totalPayTrafficFee;

    private BigDecimal totalTradeGapFee;

    private BigDecimal totalPaymentAmount;

    private BigDecimal totalLoadMoney;

    private BigDecimal loadEstimateCost;

    private BigDecimal totalUnrepaymentEstimateCost;

    private BigDecimal totalRepaymentPrincipeAmount;

    private BigDecimal totalUnpayFee;

    private BigDecimal totalUnpayInterest;

    private BigDecimal totalUnpayPrincipal;

    private BigDecimal outCapitalAmout;

    private BigDecimal totalHuikuanPaymentMoney;

    private BigDecimal payCargoAmount;

    private BigDecimal unpaymentMoney;

    private BigDecimal unpaymentEstimateProfile;

    private BigDecimal interestDays;

    private BigDecimal actualUtilizationRate;

    private BigDecimal rate;

    private BigDecimal totalPaymentedRateMoney;

    private BigDecimal contractRateProfile;

    private BigDecimal tiexianRate;

    private BigDecimal tiexianRateAmount;

    private BigDecimal totalBuyerMoney;

    private BigDecimal totalBuyerNums;

    private BigDecimal totalBuyersettleGap;

    private BigDecimal DSDDFee;

    private BigDecimal HSHYFee;

    private BigDecimal HSSYFee;

    private BigDecimal HSQYFee;

    private BigDecimal businessFee;

    private BigDecimal superviseFee;

    private BigDecimal serviceFee;

    private BigDecimal salesFeeAmount;

    private BigDecimal totalCCSInTypeMoney;

    private BigDecimal totalCSSInTypeNumber;

    private BigDecimal invoicedMoneyNum;

    private BigDecimal invoicedMoneyAmount;

    private BigDecimal finalSettleAmount;

    private BigDecimal saleCargoAmountofMoney;

    private BigDecimal tradingCompanyAddMoney;

    private BigDecimal unsettlerBuyerMoneyAmount;

    private BigDecimal unsettlerBuyerNumber;

    private BigDecimal CCSProfile;

    private BigDecimal purchaseCargoAmountofMoney;

    private BigDecimal externalCapitalPaymentAmount;

    private BigDecimal ownerCapitalPaymentAmount;

    private BigDecimal upstreamCapitalPressure;

    private BigDecimal downstreamCapitalPressure;

    private BigDecimal CCSUnInTypeNum;

    private BigDecimal CCSUnInTypeMoney;

    private BigDecimal unInvoicedAmountofMoney;

    private BigDecimal yingPrePayment;

    private BigDecimal settleGrossProfileNum;

    private BigDecimal purchaseIncludeTaxTotalAmount;

    private BigDecimal saleIncludeTaxTotalAmount;

    private BigDecimal tradeCompanyAddMoney;

    private BigDecimal withoutTaxIncome;

    private BigDecimal withoutTaxCost;

    private BigDecimal ying_VAT;

    private BigDecimal yingAdditionalTax;

    private BigDecimal stampDuty;

    private BigDecimal opreationCrossProfile;

    private BigDecimal crossProfileATon;

    private BigDecimal totalFayunNum;

    private BigDecimal totalUnarriveNum;

    private BigDecimal totalArriveNum;

    private BigDecimal totalPayDownBail;

    private BigDecimal totalRefundDownBail;

    private BigDecimal totalRefundUpBail;

    private BigDecimal totalUpstreamBail;

    private BigDecimal balanceUpstreamBail;

    private BigDecimal balanceDownStreamBail;
    //贸易公司已收到进项数量
    private BigDecimal tradingCompanyInTypeNum;

    private BigDecimal tradingCompanyInTpeMoneyAmount;

    private static final long serialVersionUID = 1L;

}