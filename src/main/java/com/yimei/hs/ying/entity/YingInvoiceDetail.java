package com.yimei.hs.ying.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
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
