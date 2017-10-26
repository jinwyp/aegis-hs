package com.yimei.hs.cang.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CangAnalysisData implements Serializable {
    private Long hsId;

    private Long orderId;

    private BigDecimal totalPayTrafficFee;

    private BigDecimal totalTradeGapFee;

    private BigDecimal totalpaymentAmount;

    private BigDecimal totalLoadMoney;

    private BigDecimal loadEstimateCost;

    private BigDecimal totalUnrepaymentEstimateCost;

    private BigDecimal totalRepaymentPrincipeAmount;

    private BigDecimal totalrepaymentFee;

    private BigDecimal totalrepaymentInterest;

    private BigDecimal totalUnpayFee;

    private BigDecimal totalUnpayInterest;

    private BigDecimal outCapitalAmout;

    private BigDecimal totalpaymentMoney;

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

    private BigDecimal HSQYFee;

    private BigDecimal HSSYFee;

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

    private BigDecimal cangPrePayment;

    private BigDecimal settleGrossProfileNum;

    private BigDecimal purchaseIncludeTaxTotalAmount;

    private BigDecimal saleIncludeTaxTotalAmount;

    private BigDecimal tradeCompanyAddMoney;

    private BigDecimal withoutTaxIncome;

    private BigDecimal withoutTaxCost;

    private BigDecimal cangAdditionalTax;

    private BigDecimal cang_VAT;

    private BigDecimal stampDuty;

    private BigDecimal opreationCrossProfile;

    private BigDecimal crossProfileATon;

    private BigDecimal totalInstorageNum;

    private BigDecimal totalInstorageAmount;

    private BigDecimal instorageUnitPrice;

    private BigDecimal totalStockNum;

    private BigDecimal totaloutstorageNum;

    private BigDecimal totalStockMoney;

    private static final long serialVersionUID = 1L;

    public Long getHsId() {
        return hsId;
    }

    public void setHsId(Long hsId) {
        this.hsId = hsId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalPayTrafficFee() {
        return totalPayTrafficFee;
    }

    public void setTotalPayTrafficFee(BigDecimal totalPayTrafficFee) {
        this.totalPayTrafficFee = totalPayTrafficFee;
    }

    public BigDecimal getTotalTradeGapFee() {
        return totalTradeGapFee;
    }

    public void setTotalTradeGapFee(BigDecimal totalTradeGapFee) {
        this.totalTradeGapFee = totalTradeGapFee;
    }

    public BigDecimal getTotalpaymentAmount() {
        return totalpaymentAmount;
    }

    public void setTotalpaymentAmount(BigDecimal totalpaymentAmount) {
        this.totalpaymentAmount = totalpaymentAmount;
    }

    public BigDecimal getTotalLoadMoney() {
        return totalLoadMoney;
    }

    public void setTotalLoadMoney(BigDecimal totalLoadMoney) {
        this.totalLoadMoney = totalLoadMoney;
    }

    public BigDecimal getLoadEstimateCost() {
        return loadEstimateCost;
    }

    public void setLoadEstimateCost(BigDecimal loadEstimateCost) {
        this.loadEstimateCost = loadEstimateCost;
    }

    public BigDecimal getTotalUnrepaymentEstimateCost() {
        return totalUnrepaymentEstimateCost;
    }

    public void setTotalUnrepaymentEstimateCost(BigDecimal totalUnrepaymentEstimateCost) {
        this.totalUnrepaymentEstimateCost = totalUnrepaymentEstimateCost;
    }

    public BigDecimal getTotalRepaymentPrincipeAmount() {
        return totalRepaymentPrincipeAmount;
    }

    public void setTotalRepaymentPrincipeAmount(BigDecimal totalRepaymentPrincipeAmount) {
        this.totalRepaymentPrincipeAmount = totalRepaymentPrincipeAmount;
    }

    public BigDecimal getTotalrepaymentFee() {
        return totalrepaymentFee;
    }

    public void setTotalrepaymentFee(BigDecimal totalrepaymentFee) {
        this.totalrepaymentFee = totalrepaymentFee;
    }

    public BigDecimal getTotalrepaymentInterest() {
        return totalrepaymentInterest;
    }

    public void setTotalrepaymentInterest(BigDecimal totalrepaymentInterest) {
        this.totalrepaymentInterest = totalrepaymentInterest;
    }

    public BigDecimal getTotalUnpayFee() {
        return totalUnpayFee;
    }

    public void setTotalUnpayFee(BigDecimal totalUnpayFee) {
        this.totalUnpayFee = totalUnpayFee;
    }

    public BigDecimal getTotalUnpayInterest() {
        return totalUnpayInterest;
    }

    public void setTotalUnpayInterest(BigDecimal totalUnpayInterest) {
        this.totalUnpayInterest = totalUnpayInterest;
    }

    public BigDecimal getOutCapitalAmout() {
        return outCapitalAmout;
    }

    public void setOutCapitalAmout(BigDecimal outCapitalAmout) {
        this.outCapitalAmout = outCapitalAmout;
    }

    public BigDecimal getTotalpaymentMoney() {
        return totalpaymentMoney;
    }

    public void setTotalpaymentMoney(BigDecimal totalpaymentMoney) {
        this.totalpaymentMoney = totalpaymentMoney;
    }

    public BigDecimal getPayCargoAmount() {
        return payCargoAmount;
    }

    public void setPayCargoAmount(BigDecimal payCargoAmount) {
        this.payCargoAmount = payCargoAmount;
    }

    public BigDecimal getUnpaymentMoney() {
        return unpaymentMoney;
    }

    public void setUnpaymentMoney(BigDecimal unpaymentMoney) {
        this.unpaymentMoney = unpaymentMoney;
    }

    public BigDecimal getUnpaymentEstimateProfile() {
        return unpaymentEstimateProfile;
    }

    public void setUnpaymentEstimateProfile(BigDecimal unpaymentEstimateProfile) {
        this.unpaymentEstimateProfile = unpaymentEstimateProfile;
    }

    public BigDecimal getInterestDays() {
        return interestDays;
    }

    public void setInterestDays(BigDecimal interestDays) {
        this.interestDays = interestDays;
    }

    public BigDecimal getActualUtilizationRate() {
        return actualUtilizationRate;
    }

    public void setActualUtilizationRate(BigDecimal actualUtilizationRate) {
        this.actualUtilizationRate = actualUtilizationRate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getTotalPaymentedRateMoney() {
        return totalPaymentedRateMoney;
    }

    public void setTotalPaymentedRateMoney(BigDecimal totalPaymentedRateMoney) {
        this.totalPaymentedRateMoney = totalPaymentedRateMoney;
    }

    public BigDecimal getContractRateProfile() {
        return contractRateProfile;
    }

    public void setContractRateProfile(BigDecimal contractRateProfile) {
        this.contractRateProfile = contractRateProfile;
    }

    public BigDecimal getTiexianRate() {
        return tiexianRate;
    }

    public void setTiexianRate(BigDecimal tiexianRate) {
        this.tiexianRate = tiexianRate;
    }

    public BigDecimal getTiexianRateAmount() {
        return tiexianRateAmount;
    }

    public void setTiexianRateAmount(BigDecimal tiexianRateAmount) {
        this.tiexianRateAmount = tiexianRateAmount;
    }

    public BigDecimal getTotalBuyerMoney() {
        return totalBuyerMoney;
    }

    public void setTotalBuyerMoney(BigDecimal totalBuyerMoney) {
        this.totalBuyerMoney = totalBuyerMoney;
    }

    public BigDecimal getTotalBuyerNums() {
        return totalBuyerNums;
    }

    public void setTotalBuyerNums(BigDecimal totalBuyerNums) {
        this.totalBuyerNums = totalBuyerNums;
    }

    public BigDecimal getTotalBuyersettleGap() {
        return totalBuyersettleGap;
    }

    public void setTotalBuyersettleGap(BigDecimal totalBuyersettleGap) {
        this.totalBuyersettleGap = totalBuyersettleGap;
    }

    public BigDecimal getDSDDFee() {
        return DSDDFee;
    }

    public void setDSDDFee(BigDecimal DSDDFee) {
        this.DSDDFee = DSDDFee;
    }

    public BigDecimal getHSHYFee() {
        return HSHYFee;
    }

    public void setHSHYFee(BigDecimal HSHYFee) {
        this.HSHYFee = HSHYFee;
    }

    public BigDecimal getHSQYFee() {
        return HSQYFee;
    }

    public void setHSQYFee(BigDecimal HSQYFee) {
        this.HSQYFee = HSQYFee;
    }

    public BigDecimal getHSSYFee() {
        return HSSYFee;
    }

    public void setHSSYFee(BigDecimal HSSYFee) {
        this.HSSYFee = HSSYFee;
    }

    public BigDecimal getBusinessFee() {
        return businessFee;
    }

    public void setBusinessFee(BigDecimal businessFee) {
        this.businessFee = businessFee;
    }

    public BigDecimal getSuperviseFee() {
        return superviseFee;
    }

    public void setSuperviseFee(BigDecimal superviseFee) {
        this.superviseFee = superviseFee;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getSalesFeeAmount() {
        return salesFeeAmount;
    }

    public void setSalesFeeAmount(BigDecimal salesFeeAmount) {
        this.salesFeeAmount = salesFeeAmount;
    }

    public BigDecimal getTotalCCSInTypeMoney() {
        return totalCCSInTypeMoney;
    }

    public void setTotalCCSInTypeMoney(BigDecimal totalCCSInTypeMoney) {
        this.totalCCSInTypeMoney = totalCCSInTypeMoney;
    }

    public BigDecimal getTotalCSSInTypeNumber() {
        return totalCSSInTypeNumber;
    }

    public void setTotalCSSInTypeNumber(BigDecimal totalCSSInTypeNumber) {
        this.totalCSSInTypeNumber = totalCSSInTypeNumber;
    }

    public BigDecimal getInvoicedMoneyNum() {
        return invoicedMoneyNum;
    }

    public void setInvoicedMoneyNum(BigDecimal invoicedMoneyNum) {
        this.invoicedMoneyNum = invoicedMoneyNum;
    }

    public BigDecimal getInvoicedMoneyAmount() {
        return invoicedMoneyAmount;
    }

    public void setInvoicedMoneyAmount(BigDecimal invoicedMoneyAmount) {
        this.invoicedMoneyAmount = invoicedMoneyAmount;
    }

    public BigDecimal getFinalSettleAmount() {
        return finalSettleAmount;
    }

    public void setFinalSettleAmount(BigDecimal finalSettleAmount) {
        this.finalSettleAmount = finalSettleAmount;
    }

    public BigDecimal getSaleCargoAmountofMoney() {
        return saleCargoAmountofMoney;
    }

    public void setSaleCargoAmountofMoney(BigDecimal saleCargoAmountofMoney) {
        this.saleCargoAmountofMoney = saleCargoAmountofMoney;
    }

    public BigDecimal getTradingCompanyAddMoney() {
        return tradingCompanyAddMoney;
    }

    public void setTradingCompanyAddMoney(BigDecimal tradingCompanyAddMoney) {
        this.tradingCompanyAddMoney = tradingCompanyAddMoney;
    }

    public BigDecimal getUnsettlerBuyerMoneyAmount() {
        return unsettlerBuyerMoneyAmount;
    }

    public void setUnsettlerBuyerMoneyAmount(BigDecimal unsettlerBuyerMoneyAmount) {
        this.unsettlerBuyerMoneyAmount = unsettlerBuyerMoneyAmount;
    }

    public BigDecimal getUnsettlerBuyerNumber() {
        return unsettlerBuyerNumber;
    }

    public void setUnsettlerBuyerNumber(BigDecimal unsettlerBuyerNumber) {
        this.unsettlerBuyerNumber = unsettlerBuyerNumber;
    }

    public BigDecimal getCCSProfile() {
        return CCSProfile;
    }

    public void setCCSProfile(BigDecimal CCSProfile) {
        this.CCSProfile = CCSProfile;
    }

    public BigDecimal getPurchaseCargoAmountofMoney() {
        return purchaseCargoAmountofMoney;
    }

    public void setPurchaseCargoAmountofMoney(BigDecimal purchaseCargoAmountofMoney) {
        this.purchaseCargoAmountofMoney = purchaseCargoAmountofMoney;
    }

    public BigDecimal getExternalCapitalPaymentAmount() {
        return externalCapitalPaymentAmount;
    }

    public void setExternalCapitalPaymentAmount(BigDecimal externalCapitalPaymentAmount) {
        this.externalCapitalPaymentAmount = externalCapitalPaymentAmount;
    }

    public BigDecimal getOwnerCapitalPaymentAmount() {
        return ownerCapitalPaymentAmount;
    }

    public void setOwnerCapitalPaymentAmount(BigDecimal ownerCapitalPaymentAmount) {
        this.ownerCapitalPaymentAmount = ownerCapitalPaymentAmount;
    }

    public BigDecimal getUpstreamCapitalPressure() {
        return upstreamCapitalPressure;
    }

    public void setUpstreamCapitalPressure(BigDecimal upstreamCapitalPressure) {
        this.upstreamCapitalPressure = upstreamCapitalPressure;
    }

    public BigDecimal getDownstreamCapitalPressure() {
        return downstreamCapitalPressure;
    }

    public void setDownstreamCapitalPressure(BigDecimal downstreamCapitalPressure) {
        this.downstreamCapitalPressure = downstreamCapitalPressure;
    }

    public BigDecimal getCCSUnInTypeNum() {
        return CCSUnInTypeNum;
    }

    public void setCCSUnInTypeNum(BigDecimal CCSUnInTypeNum) {
        this.CCSUnInTypeNum = CCSUnInTypeNum;
    }

    public BigDecimal getCCSUnInTypeMoney() {
        return CCSUnInTypeMoney;
    }

    public void setCCSUnInTypeMoney(BigDecimal CCSUnInTypeMoney) {
        this.CCSUnInTypeMoney = CCSUnInTypeMoney;
    }

    public BigDecimal getUnInvoicedAmountofMoney() {
        return unInvoicedAmountofMoney;
    }

    public void setUnInvoicedAmountofMoney(BigDecimal unInvoicedAmountofMoney) {
        this.unInvoicedAmountofMoney = unInvoicedAmountofMoney;
    }

    public BigDecimal getCangPrePayment() {
        return cangPrePayment;
    }

    public void setCangPrePayment(BigDecimal cangPrePayment) {
        this.cangPrePayment = cangPrePayment;
    }

    public BigDecimal getSettleGrossProfileNum() {
        return settleGrossProfileNum;
    }

    public void setSettleGrossProfileNum(BigDecimal settleGrossProfileNum) {
        this.settleGrossProfileNum = settleGrossProfileNum;
    }

    public BigDecimal getPurchaseIncludeTaxTotalAmount() {
        return purchaseIncludeTaxTotalAmount;
    }

    public void setPurchaseIncludeTaxTotalAmount(BigDecimal purchaseIncludeTaxTotalAmount) {
        this.purchaseIncludeTaxTotalAmount = purchaseIncludeTaxTotalAmount;
    }

    public BigDecimal getSaleIncludeTaxTotalAmount() {
        return saleIncludeTaxTotalAmount;
    }

    public void setSaleIncludeTaxTotalAmount(BigDecimal saleIncludeTaxTotalAmount) {
        this.saleIncludeTaxTotalAmount = saleIncludeTaxTotalAmount;
    }

    public BigDecimal getTradeCompanyAddMoney() {
        return tradeCompanyAddMoney;
    }

    public void setTradeCompanyAddMoney(BigDecimal tradeCompanyAddMoney) {
        this.tradeCompanyAddMoney = tradeCompanyAddMoney;
    }

    public BigDecimal getWithoutTaxIncome() {
        return withoutTaxIncome;
    }

    public void setWithoutTaxIncome(BigDecimal withoutTaxIncome) {
        this.withoutTaxIncome = withoutTaxIncome;
    }

    public BigDecimal getWithoutTaxCost() {
        return withoutTaxCost;
    }

    public void setWithoutTaxCost(BigDecimal withoutTaxCost) {
        this.withoutTaxCost = withoutTaxCost;
    }

    public BigDecimal getCangAdditionalTax() {
        return cangAdditionalTax;
    }

    public void setCangAdditionalTax(BigDecimal cangAdditionalTax) {
        this.cangAdditionalTax = cangAdditionalTax;
    }

    public BigDecimal getCang_VAT() {
        return cang_VAT;
    }

    public void setCang_VAT(BigDecimal cang_VAT) {
        this.cang_VAT = cang_VAT;
    }

    public BigDecimal getStampDuty() {
        return stampDuty;
    }

    public void setStampDuty(BigDecimal stampDuty) {
        this.stampDuty = stampDuty;
    }

    public BigDecimal getOpreationCrossProfile() {
        return opreationCrossProfile;
    }

    public void setOpreationCrossProfile(BigDecimal opreationCrossProfile) {
        this.opreationCrossProfile = opreationCrossProfile;
    }

    public BigDecimal getCrossProfileATon() {
        return crossProfileATon;
    }

    public void setCrossProfileATon(BigDecimal crossProfileATon) {
        this.crossProfileATon = crossProfileATon;
    }

    public BigDecimal getTotalInstorageNum() {
        return totalInstorageNum;
    }

    public void setTotalInstorageNum(BigDecimal totalInstorageNum) {
        this.totalInstorageNum = totalInstorageNum;
    }

    public BigDecimal getTotalInstorageAmount() {
        return totalInstorageAmount;
    }

    public void setTotalInstorageAmount(BigDecimal totalInstorageAmount) {
        this.totalInstorageAmount = totalInstorageAmount;
    }

    public BigDecimal getInstorageUnitPrice() {
        return instorageUnitPrice;
    }

    public void setInstorageUnitPrice(BigDecimal instorageUnitPrice) {
        this.instorageUnitPrice = instorageUnitPrice;
    }

    public BigDecimal getTotalStockNum() {
        return totalStockNum;
    }

    public void setTotalStockNum(BigDecimal totalStockNum) {
        this.totalStockNum = totalStockNum;
    }

    public BigDecimal getTotaloutstorageNum() {
        return totaloutstorageNum;
    }

    public void setTotaloutstorageNum(BigDecimal totaloutstorageNum) {
        this.totaloutstorageNum = totaloutstorageNum;
    }

    public BigDecimal getTotalStockMoney() {
        return totalStockMoney;
    }

    public void setTotalStockMoney(BigDecimal totalStockMoney) {
        this.totalStockMoney = totalStockMoney;
    }
}