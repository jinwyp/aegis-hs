package com.yimei.hs.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CangHuikuanMap implements Serializable {
    private Long id;

    private Long huikuanId;

    private Long fukuanId;

    private BigDecimal amount;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}

