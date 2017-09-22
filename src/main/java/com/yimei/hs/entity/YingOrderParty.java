package com.yimei.hs.entity;

import com.yimei.hs.enums.CustomerType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class YingOrderParty implements Serializable {
    private Long id;

    private Long orderId;

    private CustomerType custType;

    private Long customerId;

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

    public CustomerType getCustType() {
        return custType;
    }

    public void setCustType(CustomerType custType) {
        this.custType = custType == null ? null : custType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getTsc() {
        return tsc;
    }

    public void setTsc(LocalDateTime tsc) {
        this.tsc = tsc;
    }
}