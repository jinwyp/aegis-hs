package com.yimei.hs.cang.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CangChuku implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private LocalDateTime chukuDate;

    private String locality;

    private BigDecimal chukuAmount;

    private BigDecimal chukuPrice;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}

