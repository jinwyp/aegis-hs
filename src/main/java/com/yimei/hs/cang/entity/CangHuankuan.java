package com.yimei.hs.cang.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CangHuankuan implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private Long skCompanyId;

    private LocalDateTime huankuankDate;

    private BigDecimal huankuanAmount;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
