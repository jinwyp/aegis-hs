package com.yimei.hs.entity;

import com.yimei.hs.enums.PayMode;
import com.yimei.hs.enums.PaymentPurpose;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class YingFukuan implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private LocalDateTime payDate;

    private Long recieveCompanyId;

    private PaymentPurpose payUsage;

    private BigDecimal payAmount;

    private PayMode payMode;

    private Long capitalId;

    private BigDecimal useInterest;

    private Integer useDays;

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

    public LocalDateTime getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDateTime payDate) {
        this.payDate = payDate;
    }

    public Long getRecieveCompanyId() {
        return recieveCompanyId;
    }

    public void setRecieveCompanyId(Long recieveCompanyId) {
        this.recieveCompanyId = recieveCompanyId;
    }

    public PaymentPurpose getPayUsage() {
        return payUsage;
    }

    public void setPayUsage(PaymentPurpose payUsage) {
        this.payUsage = payUsage == null ? null : payUsage;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public PayMode getPayMode() {
        return payMode;
    }

    public void setPayMode(PayMode payMode) {
        this.payMode = payMode == null ? null : payMode;
    }

    public Long getCapitalId() {
        return capitalId;
    }

    public void setCapitalId(Long capitalId) {
        this.capitalId = capitalId;
    }

    public BigDecimal getUseInterest() {
        return useInterest;
    }

    public void setUseInterest(BigDecimal useInterest) {
        this.useInterest = useInterest;
    }

    public Integer getUseDays() {
        return useDays;
    }

    public void setUseDays(Integer useDays) {
        this.useDays = useDays;
    }

    public LocalDateTime getTsc() {
        return tsc;
    }

    public void setTsc(LocalDateTime tsc) {
        this.tsc = tsc;
    }
}