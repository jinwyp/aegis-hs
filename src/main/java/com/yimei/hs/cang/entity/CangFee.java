package com.yimei.hs.cang.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CangFee implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private String name;

    private BigDecimal amount;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
