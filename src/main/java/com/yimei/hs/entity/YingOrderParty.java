package com.yimei.hs.entity;

import java.io.Serializable;
import java.util.Date;

public class YingOrderParty implements Serializable {
    private Long id;

    private Long orderId;

    private String custType;

    private Long customerId;

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

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType == null ? null : custType.trim();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getTsc() {
        return tsc;
    }

    public void setTsc(Date tsc) {
        this.tsc = tsc;
    }
}