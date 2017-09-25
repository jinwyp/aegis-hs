package com.yimei.hs.cang.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class CangHuikuan implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private Long huikuanCompanyId;

    private LocalDateTime huikuanDate;

    private BigDecimal huikuanAmount;

    private String huikuanUsage;

    private String huikuanMode;

    private Boolean huikuanPaper;

    private LocalDateTime huikuanPaperDate;

    private Boolean huikuanDiscount;

    private BigDecimal huikuanDiscountRate;

    private LocalDateTime huikuanPaperExpire;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
