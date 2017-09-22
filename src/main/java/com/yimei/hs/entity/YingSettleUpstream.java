package com.yimei.hs.entity;

import com.yimei.hs.enums.DiscountMode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class YingSettleUpstream implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private LocalDateTime settleDate;

    private BigDecimal amount;

    private BigDecimal money;

    private DiscountMode discountType;

    private BigDecimal discountInterest;

    private Integer discountDays;

    private BigDecimal discountAmount;

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

    public LocalDateTime getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(LocalDateTime settleDate) {
        this.settleDate = settleDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public DiscountMode getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountMode discountType) {
        this.discountType = discountType == null ? null : discountType;
    }

    public BigDecimal getDiscountInterest() {
        return discountInterest;
    }

    public void setDiscountInterest(BigDecimal discountInterest) {
        this.discountInterest = discountInterest;
    }

    public Integer getDiscountDays() {
        return discountDays;
    }

    public void setDiscountDays(Integer discountDays) {
        this.discountDays = discountDays;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public LocalDateTime getTsc() {
        return tsc;
    }

    public void setTsc(LocalDateTime tsc) {
        this.tsc = tsc;
    }
}