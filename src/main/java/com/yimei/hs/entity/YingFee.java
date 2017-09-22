package com.yimei.hs.entity;

import com.yimei.hs.enums.FeeClass;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class YingFee implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private FeeClass name;

    private BigDecimal amount;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

    
}