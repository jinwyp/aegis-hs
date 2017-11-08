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

    private BigDecimal totalServiceCharge;

    private BigDecimal totalUnpayPrincipal;

    private BigDecimal totalRepaymentInterest;

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

    private BigDecimal dsddFee;

    private BigDecimal hshyFee;

    private BigDecimal hssyFee;

    private BigDecimal hsqyFee;

    private BigDecimal businessFee;

    private BigDecimal superviseFee;

    private BigDecimal serviceFee;

    private BigDecimal salesFeeAmount;

    private BigDecimal totalCCSIntypeMoney;

    private BigDecimal totalCSSIntypeNumber;

    private BigDecimal InvoicedMoneyNum;

    private BigDecimal invoicedMoneyAmount;

    private BigDecimal finalSettleAmount;

    private BigDecimal saleCargoAmountofMoney;

    private BigDecimal tradingCompanyAddMoney;

    private BigDecimal unsettlerBuyerMoneyAmount;

    private BigDecimal unsettlerBuyerNumber;

    private BigDecimal ccsProfile;

    private BigDecimal purchaseCargoAmountOfMoney;

    private BigDecimal externalCapitalPaymentAmount;

    private BigDecimal ownerCapitalPaymentAmount;

    private BigDecimal upstreamCapitalPressure;

    private BigDecimal downstreamCapitalPressure;

    private BigDecimal cssUninTypeNum;

    private BigDecimal cssUninTypeMoney;

    private BigDecimal unInvoicedAmountofMoney;

    private BigDecimal yingPrePayment;

    private BigDecimal settleGrossProfileNum;

    private BigDecimal purchaseIncludeTaxTotalAmount;

    private BigDecimal saleIncludeTaxTotalAmount;

    private BigDecimal tradeCompanyAddMoney;

    private BigDecimal withoutTaxIncome;

    private BigDecimal withoutTaxCost;

    private BigDecimal vat;

    private BigDecimal additionalTax;

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
    //下游已结算未回款金额
    private BigDecimal settledDownstreamHuikuanMoneny;

    private BigDecimal ownerCapitalPressure;

    private static final long serialVersionUID = 1L;

}