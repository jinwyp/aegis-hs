package com.yimei.hs.entity;

import lombok.Data;

import javax.validation.constraints.Max;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class YingHuankuan implements Serializable {
    private Long id;
    @Max(20)
    private Long orderId;
    @Max(20)
    private Long hsId;

    private Long skCompanyId;

    private LocalDateTime huankuankDate;

    private BigDecimal huankuanAmount;

    private BigDecimal huankuanInterest;

    private BigDecimal huankuanFee;

    private LocalDateTime tsc;

    private List<YingFukuan> fukuanList;

    private static final long serialVersionUID = 1L;
}

