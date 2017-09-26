package com.yimei.hs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class YingHuankuan implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private Long skCompanyId;

    private LocalDateTime huankuankDate;

    private BigDecimal huankuanAmount;

    private BigDecimal huankuanInterest;

    private BigDecimal huankuanFee;

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

    public Long getSkCompanyId() {
        return skCompanyId;
    }

    public void setSkCompanyId(Long skCompanyId) {
        this.skCompanyId = skCompanyId;
    }

    public LocalDateTime getHuankuankDate() {
        return huankuankDate;
    }

    public void setHuankuankDate(LocalDateTime huankuankDate) {
        this.huankuankDate = huankuankDate;
    }

    public BigDecimal getHuankuanAmount() {
        return huankuanAmount;
    }

    public void setHuankuanAmount(BigDecimal huankuanAmount) {
        this.huankuanAmount = huankuanAmount;
    }

    public BigDecimal getHuankuanInterest() {
        return huankuanInterest;
    }

    public void setHuankuanInterest(BigDecimal huankuanInterest) {
        this.huankuanInterest = huankuanInterest;
    }

    public BigDecimal getHuankuanFee() {
        return huankuanFee;
    }

    public void setHuankuanFee(BigDecimal huankuanFee) {
        this.huankuanFee = huankuanFee;
    }

    public LocalDateTime getTsc() {
        return tsc;
    }

    public void setTsc(LocalDateTime tsc) {
        this.tsc = tsc;
    }
}