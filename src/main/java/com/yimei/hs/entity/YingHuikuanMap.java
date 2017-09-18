package com.yimei.hs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class YingHuikuanMap implements Serializable {
    private Long id;

    private Long huikuanId;

    private Long fukuanId;

    private BigDecimal amount;

    private Date tsc;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getTsc() {
        return tsc;
    }

    public void setTsc(Date tsc) {
        this.tsc = tsc;
    }
}