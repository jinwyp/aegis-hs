package com.yimei.hs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CangHuankuan implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private Long skCompanyId;

    private Date huankuankDate;

    private BigDecimal huankuanAmount;

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

    public Long getSkCompanyId() {
        return skCompanyId;
    }

    public void setSkCompanyId(Long skCompanyId) {
        this.skCompanyId = skCompanyId;
    }

    public Date getHuankuankDate() {
        return huankuankDate;
    }

    public void setHuankuankDate(Date huankuankDate) {
        this.huankuankDate = huankuankDate;
    }

    public BigDecimal getHuankuanAmount() {
        return huankuanAmount;
    }

    public void setHuankuanAmount(BigDecimal huankuanAmount) {
        this.huankuanAmount = huankuanAmount;
    }

    public Date getTsc() {
        return tsc;
    }

    public void setTsc(Date tsc) {
        this.tsc = tsc;
    }
}