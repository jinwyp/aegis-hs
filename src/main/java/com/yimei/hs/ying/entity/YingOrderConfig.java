package com.yimei.hs.ying.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class YingOrderConfig implements Serializable {
    private Long id;

    @NotNull(message = "订单id不能为空")
    private Long orderId;

    @NotNull(message = "结算月不能为空")
    private String hsMonth;

    private BigDecimal maxPrepayRate;

    private BigDecimal unInvoicedRate;

    private BigDecimal contractBaseInterest;

    private Integer expectHKDays;

    private BigDecimal tradeAddPrice;

    private BigDecimal weightedPrice;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
