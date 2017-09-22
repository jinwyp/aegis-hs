package com.yimei.hs.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class YingSettleTraffic implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private LocalDateTime settleDate;

    private BigDecimal amount;

    private BigDecimal money;

    private Long trafficCompanyId;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}