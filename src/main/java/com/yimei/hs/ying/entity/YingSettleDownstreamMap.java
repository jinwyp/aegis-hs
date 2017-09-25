package com.yimei.hs.ying.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class YingSettleDownstreamMap implements Serializable {
    private Long id;

    private Long settleId;

    private Long fyId;

    private BigDecimal deduction;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;


}