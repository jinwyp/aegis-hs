package com.yimei.hs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CangHuankuanMap implements Serializable {
    private Long id;

    private Long huankuanId;

    private Long fukuanId;

    private BigDecimal principal;

    private BigDecimal amount;

    private Date tsc;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHuankuanId() {
        return huankuanId;
    }

    public void setHuankuanId(Long huankuanId) {
        this.huankuanId = huankuanId;
    }

    public Long getFukuanId() {
        return fukuanId;
    }

    public void setFukuanId(Long fukuanId) {
        this.fukuanId = fukuanId;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
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