package com.yimei.hs.ying.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class YingInvoiceDetail implements Serializable {
    private Long id;

    private Long invoiceId;

    private String invoiceNumber;

    private BigDecimal cargoAmount;

    private BigDecimal taxRate;

    private BigDecimal priceAndTax;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
