package com.yimei.hs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CangChuku implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private LocalDateTime chukuDate;

    private String locality;

    private BigDecimal chukuAmount;

    private BigDecimal chukuPrice;

    private LocalDateTime tsc;

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

    public Long getHsId() {
        return hsId;
    }

    public void setHsId(Long hsId) {
        this.hsId = hsId;
    }

    public LocalDateTime getChukuDate() {
        return chukuDate;
    }

    public void setChukuDate(LocalDateTime chukuDate) {
        this.chukuDate = chukuDate;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality == null ? null : locality.trim();
    }

    public BigDecimal getChukuAmount() {
        return chukuAmount;
    }

    public void setChukuAmount(BigDecimal chukuAmount) {
        this.chukuAmount = chukuAmount;
    }

    public BigDecimal getChukuPrice() {
        return chukuPrice;
    }

    public void setChukuPrice(BigDecimal chukuPrice) {
        this.chukuPrice = chukuPrice;
    }

    public LocalDateTime getTsc() {
        return tsc;
    }

    public void setTsc(LocalDateTime tsc) {
        this.tsc = tsc;
    }
}