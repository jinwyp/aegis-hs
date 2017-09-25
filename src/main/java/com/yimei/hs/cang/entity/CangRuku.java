package com.yimei.hs.cang.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CangRuku implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private LocalDateTime rukuDate;

    private Integer rukuStatus;

    private BigDecimal rukuAmount;

    private BigDecimal rukuPrice;

    private String locality;

    private String trafficMode;

    private Integer cars;

    private String jhh;

    private String ship;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
