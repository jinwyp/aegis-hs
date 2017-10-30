package com.yimei.hs.cang.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class CangAnalysisData implements Serializable {
    private Long hsId;

    private Long orderId;

    private BigDecimal totalPayTrafficFee;

    private BigDecimal totalTradeGapFee;

    private BigDecimal totalPaymentAmount;

    private BigDecimal totalLoadMoney;

    private BigDecimal loadEstimateCost;

    private BigDecimal totalUnrepaymentEstimateCost;

    private BigDecimal totalRepaymentPrincipeAmount;

    private BigDecimal totalRepaymentFee;

    private BigDecimal totalRepaymentInterest;

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

    private BigDecimal dsddFee;

    private BigDecimal hssyFee;

    private BigDecimal hsqyFee;

    private BigDecimal hshyFee;

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

    private BigDecimal ccsProfile;

    private BigDecimal purchaseCargoAmountofMoney;

    private BigDecimal externalCapitalPaymentAmount;

    private BigDecimal ownerCapitalPaymentAmount;

    private BigDecimal upstreamCapitalPressure;

    private BigDecimal downstreamCapitalPressure;

    private BigDecimal cssUninTypeNum;

    private BigDecimal cssUninTypeMoney;

    private BigDecimal unInvoicedAmountofMoney;

    private BigDecimal cangPrePayment;

    private BigDecimal settleGrossProfileNum;

    private BigDecimal purchaseIncludeTaxTotalAmount;

    private BigDecimal saleIncludeTaxTotalAmount;

    private BigDecimal tradeCompanyAddMoney;

    private BigDecimal withoutTaxIncome;

    private BigDecimal totalServiceCharge;

    private BigDecimal withoutTaxCost;

    private BigDecimal additionalTax;

    private BigDecimal vat;

    private BigDecimal stampDuty;

    private BigDecimal opreationCrossProfile;

    private BigDecimal crossProfileATon;

    private BigDecimal totalInstorageNum;

    private BigDecimal totalInstorageAmount;

    private BigDecimal instorageUnitPrice;

    private BigDecimal totalStockNum;

    private BigDecimal totalOutstorageNum;

    private BigDecimal totalStockMoney;

    //贸易公司已收到进项数量
    private BigDecimal tradingCompanyInTypeNum;

    private BigDecimal tradingCompanyInTpeMoneyAmount;

    private BigDecimal ownerCapitalPressure;
    private BigDecimal totalOutstorageMoney;

    private static final long serialVersionUID = 1L;

}