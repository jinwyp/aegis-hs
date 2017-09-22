package com.yimei.hs.entity;

import com.yimei.hs.enums.DiscountMode;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class YingSettleUpstream implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private LocalDateTime settleDate;

    private BigDecimal amount;

    private BigDecimal money;

    private DiscountMode discountType;

    private BigDecimal discountInterest;

    private Integer discountDays;

    private BigDecimal discountAmount;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;


}