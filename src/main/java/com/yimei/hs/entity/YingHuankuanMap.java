package com.yimei.hs.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class YingHuankuanMap implements Serializable {
    private Long id;

    private Long huankuanId;

    private Long fukuanId;

    private BigDecimal principal;

    private BigDecimal amount;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
