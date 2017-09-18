package com.yimei.hs.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class YingInvoice implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private String invoiceDirection;

    private String invoiceType;

    private LocalDateTime openDate;

    private Long openCompanyId;

    private Long recieverId;

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

    public String getInvoiceDirection() {
        return invoiceDirection;
    }

    public void setInvoiceDirection(String invoiceDirection) {
        this.invoiceDirection = invoiceDirection == null ? null : invoiceDirection.trim();
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    public LocalDateTime getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDateTime openDate) {
        this.openDate = openDate;
    }

    public Long getOpenCompanyId() {
        return openCompanyId;
    }

    public void setOpenCompanyId(Long openCompanyId) {
        this.openCompanyId = openCompanyId;
    }

    public Long getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(Long recieverId) {
        this.recieverId = recieverId;
    }

    public LocalDateTime getTsc() {
        return tsc;
    }

    public void setTsc(LocalDateTime tsc) {
        this.tsc = tsc;
    }
}