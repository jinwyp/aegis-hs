package com.yimei.hs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class YingInvoiceDetail implements Serializable {
    private Long id;

    private Long invoiceId;

    private String invoiceNumber;

    private BigDecimal cargoAmount;

    private BigDecimal taxRate;

    private BigDecimal priceAndTax;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber == null ? null : invoiceNumber.trim();
    }

    public BigDecimal getCargoAmount() {
        return cargoAmount;
    }

    public void setCargoAmount(BigDecimal cargoAmount) {
        this.cargoAmount = cargoAmount;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getPriceAndTax() {
        return priceAndTax;
    }

    public void setPriceAndTax(BigDecimal priceAndTax) {
        this.priceAndTax = priceAndTax;
    }

    public LocalDateTime getTsc() {
        return tsc;
    }

    public void setTsc(LocalDateTime tsc) {
        this.tsc = tsc;
    }
}