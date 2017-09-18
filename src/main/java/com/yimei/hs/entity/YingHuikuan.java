package com.yimei.hs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class YingHuikuan implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private Long huikuanCompanyId;

    private Date huikuanDate;

    private BigDecimal huikuanAmount;

    private String huikuanUsage;

    private String huikuanMode;

    private Byte huikuanPaper;

    private Date huikuanPaperDate;

    private Byte huikuanDiscount;

    private BigDecimal huikuanDiscountRate;

    private Date huikuanPaperExpire;

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

    public Date getHuikuanDate() {
        return huikuanDate;
    }

    public void setHuikuanDate(Date huikuanDate) {
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

    public Byte getHuikuanPaper() {
        return huikuanPaper;
    }

    public void setHuikuanPaper(Byte huikuanPaper) {
        this.huikuanPaper = huikuanPaper;
    }

    public Date getHuikuanPaperDate() {
        return huikuanPaperDate;
    }

    public void setHuikuanPaperDate(Date huikuanPaperDate) {
        this.huikuanPaperDate = huikuanPaperDate;
    }

    public Byte getHuikuanDiscount() {
        return huikuanDiscount;
    }

    public void setHuikuanDiscount(Byte huikuanDiscount) {
        this.huikuanDiscount = huikuanDiscount;
    }

    public BigDecimal getHuikuanDiscountRate() {
        return huikuanDiscountRate;
    }

    public void setHuikuanDiscountRate(BigDecimal huikuanDiscountRate) {
        this.huikuanDiscountRate = huikuanDiscountRate;
    }

    public Date getHuikuanPaperExpire() {
        return huikuanPaperExpire;
    }

    public void setHuikuanPaperExpire(Date huikuanPaperExpire) {
        this.huikuanPaperExpire = huikuanPaperExpire;
    }

    public Date getTsc() {
        return tsc;
    }

    public void setTsc(Date tsc) {
        this.tsc = tsc;
    }
}