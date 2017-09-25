package com.yimei.hs.cang.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class CangFukuan implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private LocalDateTime payDate;

    private Long recieveCompanyId;

    private String payFor;

    private BigDecimal payAmount;

    private String payMode;

    private Long capitalId;

    private BigDecimal useInterest;

    private Integer useDays;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
