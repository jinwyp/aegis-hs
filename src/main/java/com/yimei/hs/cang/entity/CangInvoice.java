package com.yimei.hs.cang.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CangInvoice implements Serializable {
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
}