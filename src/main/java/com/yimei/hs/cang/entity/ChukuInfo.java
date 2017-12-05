package com.yimei.hs.cang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChukuInfo {
    private BigDecimal totalOutstorageNum;

    private BigDecimal totalOutstorageMoney;

    /**
     *  本月未出库吨数
     */
    private BigDecimal unChukuTotalAmount;
    /**
     * 本月未出库金额
     */
    private BigDecimal unChukuTotalPrice;

    /**
     * 本月可出口金额
     */
    private BigDecimal canChukuPrice;
    /**
     * 本月可出库吨数
     */
    private BigDecimal canChukuAmount;

}
