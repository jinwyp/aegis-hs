package com.yimei.hs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class YingHuikuan implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private Long huikuanCompanyId;

    private LocalDateTime huikuanDate;

    private BigDecimal huikuanAmount;

    private String huikuanUsage;

    private String huikuanMode;

    private Boolean huikuanBankPaper;

    private LocalDateTime huikuanBankPaperDate;

    private Boolean huikuanBankDiscount;

    private BigDecimal huikuanBankDiscountRate;

    private LocalDateTime huikuanBankPaperExpire;

    private Boolean huikuanBusinessPaper;

    private LocalDateTime huikuanBusinessPaperDate;

    private Boolean huikuanBusinessDiscount;

    private BigDecimal huikuanBusinessDiscountRate;

    private LocalDateTime huikuanBusinessPaperExpire;

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

    public Boolean getHuikuanBankPaper() {
        return huikuanBankPaper;
    }

    public void setHuikuanBankPaper(Boolean huikuanBankPaper) {
        this.huikuanBankPaper = huikuanBankPaper;
    }

    public LocalDateTime getHuikuanBankPaperDate() {
        return huikuanBankPaperDate;
    }

    public void setHuikuanBankPaperDate(LocalDateTime huikuanBankPaperDate) {
        this.huikuanBankPaperDate = huikuanBankPaperDate;
    }

    public Boolean getHuikuanBankDiscount() {
        return huikuanBankDiscount;
    }

    public void setHuikuanBankDiscount(Boolean huikuanBankDiscount) {
        this.huikuanBankDiscount = huikuanBankDiscount;
    }

    public BigDecimal getHuikuanBankDiscountRate() {
        return huikuanBankDiscountRate;
    }

    public void setHuikuanBankDiscountRate(BigDecimal huikuanBankDiscountRate) {
        this.huikuanBankDiscountRate = huikuanBankDiscountRate;
    }

    public LocalDateTime getHuikuanBankPaperExpire() {
        return huikuanBankPaperExpire;
    }

    public void setHuikuanBankPaperExpire(LocalDateTime huikuanBankPaperExpire) {
        this.huikuanBankPaperExpire = huikuanBankPaperExpire;
    }

    public Boolean getHuikuanBusinessPaper() {
        return huikuanBusinessPaper;
    }

    public void setHuikuanBusinessPaper(Boolean huikuanBusinessPaper) {
        this.huikuanBusinessPaper = huikuanBusinessPaper;
    }

    public LocalDateTime getHuikuanBusinessPaperDate() {
        return huikuanBusinessPaperDate;
    }

    public void setHuikuanBusinessPaperDate(LocalDateTime huikuanBusinessPaperDate) {
        this.huikuanBusinessPaperDate = huikuanBusinessPaperDate;
    }

    public Boolean getHuikuanBusinessDiscount() {
        return huikuanBusinessDiscount;
    }

    public void setHuikuanBusinessDiscount(Boolean huikuanBusinessDiscount) {
        this.huikuanBusinessDiscount = huikuanBusinessDiscount;
    }

    public BigDecimal getHuikuanBusinessDiscountRate() {
        return huikuanBusinessDiscountRate;
    }

    public void setHuikuanBusinessDiscountRate(BigDecimal huikuanBusinessDiscountRate) {
        this.huikuanBusinessDiscountRate = huikuanBusinessDiscountRate;
    }

    public LocalDateTime getHuikuanBusinessPaperExpire() {
        return huikuanBusinessPaperExpire;
    }

    public void setHuikuanBusinessPaperExpire(LocalDateTime huikuanBusinessPaperExpire) {
        this.huikuanBusinessPaperExpire = huikuanBusinessPaperExpire;
    }

    public LocalDateTime getTsc() {
        return tsc;
    }

    public void setTsc(LocalDateTime tsc) {
        this.tsc = tsc;
    }
}