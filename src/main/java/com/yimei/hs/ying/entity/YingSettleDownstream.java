package com.yimei.hs.ying.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YingSettleDownstream implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private LocalDateTime settleDate;

    private BigDecimal amount;

    private BigDecimal money;

    private BigDecimal settleGap;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
