package com.yimei.hs.same.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Jiekuan implements Serializable {
    private Long id;

    private Long fukuanId;

    private Long orderId;

    private Long hsId;

    private BigDecimal amount;

    private Long capitalId;

    private BigDecimal useInterest;

    private Integer useDays;

    private Boolean deleted;

    private Date tsc;

    private Date tsu;

    private static final long serialVersionUID = 1L;
}