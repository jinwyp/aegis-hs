package com.yimei.hs.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CangSettleDownstream implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private LocalDateTime settleDate;

    private BigDecimal amount;

    private BigDecimal money;

    private String discountType;

    private BigDecimal discountInterest;

    private Integer discountDays;

    private BigDecimal discountAmount;

    private LocalDateTime tsc;

    // 下游结算关联了多个上游发运数据
    private List<YingFayun> fayunList;

    private static final long serialVersionUID = 1L;
}

