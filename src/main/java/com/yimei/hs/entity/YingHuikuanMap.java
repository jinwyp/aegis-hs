package com.yimei.hs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class YingHuikuanMap implements Serializable {
    private Long id;

    private Long orderId;

    private Long huikuanId;

    private Long fukuanId;

    private BigDecimal amount;

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

    public Long getHuikuanId() {
        return huikuanId;
    }

    public void setHuikuanId(Long huikuanId) {
        this.huikuanId = huikuanId;
    }

    public Long getFukuanId() {
        return fukuanId;
    }

    public void setFukuanId(Long fukuanId) {
        this.fukuanId = fukuanId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTsc() {
        return tsc;
    }

    public void setTsc(LocalDateTime tsc) {
        this.tsc = tsc;
    }
}