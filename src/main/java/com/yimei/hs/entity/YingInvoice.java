package com.yimei.hs.entity;

import com.yimei.hs.enums.InvoiceDirection;
import com.yimei.hs.enums.InvoiceType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class YingInvoice implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private InvoiceDirection invoiceDirection;

    private InvoiceType invoiceType;

    private LocalDateTime openDate;

    private Long openCompanyId;

    private Long recieverId;

    private LocalDateTime tsc;

    List<YingInvoiceDetail> details;

    public List<YingInvoiceDetail> getDetails() {
        return details;
    }

    public void setDetails(List<YingInvoiceDetail> details) {
        this.details = details;
    }

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

    public InvoiceDirection getInvoiceDirection() {
        return invoiceDirection;
    }

    public void setInvoiceDirection(InvoiceDirection invoiceDirection) {
        this.invoiceDirection = invoiceDirection == null ? null : invoiceDirection;
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType;
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