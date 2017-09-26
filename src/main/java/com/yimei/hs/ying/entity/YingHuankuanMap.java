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
public class YingHuankuanMap implements Serializable {
    private Long id;

    private Long orderId;

    private Long huankuanId;

    private Long fukuanId;

    private BigDecimal principal;

    private BigDecimal amount;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
