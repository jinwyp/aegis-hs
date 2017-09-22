package com.yimei.hs.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CangOrderConfig implements Serializable {
    private Long id;

    private Long orderId;

    private String hsMonth;

    private BigDecimal maxPrepayRate;

    private BigDecimal unInvoicedRate;

    private BigDecimal contractBaseInterest;

    private Integer expectHKDays;

    private BigDecimal tradeAddPrice;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
