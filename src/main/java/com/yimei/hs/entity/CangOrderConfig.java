package com.yimei.hs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CangOrderConfig implements Serializable {
    private Long id;

    private Long orderId;

    private String hsMonth;

    private BigDecimal maxPrepayRate;

    private BigDecimal unInvoicedRate;

    private BigDecimal contractBaseInterest;

    private Integer expectHKDays;

    private BigDecimal tradeAddPrice;

    private Date tsc;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getHsMonth() {
        return hsMonth;
    }

    public void setHsMonth(String hsMonth) {
        this.hsMonth = hsMonth == null ? null : hsMonth.trim();
    }

    public BigDecimal getMaxPrepayRate() {
        return maxPrepayRate;
    }

    public void setMaxPrepayRate(BigDecimal maxPrepayRate) {
        this.maxPrepayRate = maxPrepayRate;
    }

    public BigDecimal getUnInvoicedRate() {
        return unInvoicedRate;
    }

    public void setUnInvoicedRate(BigDecimal unInvoicedRate) {
        this.unInvoicedRate = unInvoicedRate;
    }

    public BigDecimal getContractBaseInterest() {
        return contractBaseInterest;
    }

    public void setContractBaseInterest(BigDecimal contractBaseInterest) {
        this.contractBaseInterest = contractBaseInterest;
    }

    public Integer getExpectHKDays() {
        return expectHKDays;
    }

    public void setExpectHKDays(Integer expectHKDays) {
        this.expectHKDays = expectHKDays;
    }

    public BigDecimal getTradeAddPrice() {
        return tradeAddPrice;
    }

    public void setTradeAddPrice(BigDecimal tradeAddPrice) {
        this.tradeAddPrice = tradeAddPrice;
    }

    public Date getTsc() {
        return tsc;
    }

    public void setTsc(Date tsc) {
        this.tsc = tsc;
    }
}