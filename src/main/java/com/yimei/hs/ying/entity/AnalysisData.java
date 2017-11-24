package com.yimei.hs.ying.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisData implements Serializable {

    private Long hsId;

    private Long orderId;

    private String hsMonth;

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


    private BigDecimal totalInstorageNum;

    private BigDecimal totalInstorageAmount;

    private BigDecimal instorageUnitPrice;

    private BigDecimal totalStockNum;

    private BigDecimal totalOutstorageNum;

    private BigDecimal totalStockMoney;

    private BigDecimal cangPrePayment;

    private BigDecimal totalOutstorageMoney;

    // 本月最高付款金额(本次应付货款金额)
    private BigDecimal maximumPaymentAmount;
    // 本月累计付款金额
    private BigDecimal unitTotalPaymentAmount;
    //业务累计付款金额
    private BigDecimal accumulativePaymentAmount;
    //    本次应付货款金额：amountCargoOfThisTime
    private BigDecimal amountCargoOfThisTime;

    //    累计未还款金额数据
    private BigDecimal nonRepaymentLoanMoney;

    private BigDecimal totalPayGoodsFee;


    //入库在途中数量
    private BigDecimal totalInstorageTranitNum;
    //入库在途中金额

    private BigDecimal totalInstorageTranitPrice;

    private BigDecimal totalInstoragedNum;


    private BigDecimal totalInstoragedNumMoney;

    private BigDecimal totalInstorageRemainNum;//剩余库存数量

    private BigDecimal totalInstorageRemainPrice; //剩余库存金额

    private BigDecimal totalSumInstorageNum; //合计数量

    private BigDecimal totalSumInstoragePrice; //合计金额

    private BigDecimal inOutRate;


    //     费用及补税
    private BigDecimal feeAndAddTax;
    //    毛利含税汽运费
    private BigDecimal hsqyCrossProfile;
    //    毛利含税水运费
    private BigDecimal hssyCrossProfile;
    //    毛利含税火运费
    private BigDecimal hshyCrossProfile;

    //    毛利监管费
    private BigDecimal superviseCrossProfile;
    /**
     * 服务管理费
     */
    private BigDecimal serviceCrossProfile;


}