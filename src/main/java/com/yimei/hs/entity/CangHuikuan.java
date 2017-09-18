package com.yimei.hs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CangHuikuan implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private Long huikuanCompanyId;

    private LocalDateTime huikuanDate;

    private BigDecimal huikuanAmount;

    private String huikuanUsage;

    private String huikuanMode;

    private Boolean huikuanPaper;

    private LocalDateTime huikuanPaperDate;

    private Boolean huikuanDiscount;

    private BigDecimal huikuanDiscountRate;

    private LocalDateTime huikuanPaperExpire;

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

    public Long getHuikuanCompanyId() {
        return huikuanCompanyId;
    }

    public void setHuikuanCompanyId(Long huikuanCompanyId) {
        this.huikuanCompanyId = huikuanCompanyId;
    }

    public LocalDateTime getHuikuanDate() {
        return huikuanDate;
    }

    public void setHuikuanDate(LocalDateTime huikuanDate) {
        this.huikuanDate = huikuanDate;
    }

    public BigDecimal getHuikuanAmount() {
        return huikuanAmount;
    }

    public void setHuikuanAmount(BigDecimal huikuanAmount) {
        this.huikuanAmount = huikuanAmount;
    }

    public String getHuikuanUsage() {
        return huikuanUsage;
    }

    public void setHuikuanUsage(String huikuanUsage) {
        this.huikuanUsage = huikuanUsage == null ? null : huikuanUsage.trim();
    }

    public String getHuikuanMode() {
        return huikuanMode;
    }

    public void setHuikuanMode(String huikuanMode) {
        this.huikuanMode = huikuanMode == null ? null : huikuanMode.trim();
    }

    public Boolean getHuikuanPaper() {
        return huikuanPaper;
    }

    public void setHuikuanPaper(Boolean huikuanPaper) {
        this.huikuanPaper = huikuanPaper;
    }

    public LocalDateTime getHuikuanPaperDate() {
        return huikuanPaperDate;
    }

    public void setHuikuanPaperDate(LocalDateTime huikuanPaperDate) {
        this.huikuanPaperDate = huikuanPaperDate;
    }

    public Boolean getHuikuanDiscount() {
        return huikuanDiscount;
    }

    public void setHuikuanDiscount(Boolean huikuanDiscount) {
        this.huikuanDiscount = huikuanDiscount;
    }

    public BigDecimal getHuikuanDiscountRate() {
        return huikuanDiscountRate;
    }

    public void setHuikuanDiscountRate(BigDecimal huikuanDiscountRate) {
        this.huikuanDiscountRate = huikuanDiscountRate;
    }

    public LocalDateTime getHuikuanPaperExpire() {
        return huikuanPaperExpire;
    }

    public void setHuikuanPaperExpire(LocalDateTime huikuanPaperExpire) {
        this.huikuanPaperExpire = huikuanPaperExpire;
    }

    public LocalDateTime getTsc() {
        return tsc;
    }

    public void setTsc(LocalDateTime tsc) {
        this.tsc = tsc;
    }
}