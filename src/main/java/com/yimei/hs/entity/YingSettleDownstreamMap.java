package com.yimei.hs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class YingSettleDownstreamMap implements Serializable {
    private Long id;

    private Long settleId;

    private Long fyId;

    private BigDecimal deduction;

    private Date tsc;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSettleId() {
        return settleId;
    }

    public void setSettleId(Long settleId) {
        this.settleId = settleId;
    }

    public Long getFyId() {
        return fyId;
    }

    public void setFyId(Long fyId) {
        this.fyId = fyId;
    }

    public BigDecimal getDeduction() {
        return deduction;
    }

    public void setDeduction(BigDecimal deduction) {
        this.deduction = deduction;
    }

    public Date getTsc() {
        return tsc;
    }

    public void setTsc(Date tsc) {
        this.tsc = tsc;
    }
}