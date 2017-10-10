package com.yimei.hs.same.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderConfig implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空")
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "业务线不能为空")
    private Long orderId;

    @NotNull(groups = {CreateGroup.class}, message = "核算月月份不能为空")
    private String hsMonth;

    @NotNull(groups = {CreateGroup.class}, message = "最大预付比例不能为空")
    private BigDecimal maxPrepayRate;

    @NotNull(groups = {CreateGroup.class}, message = "未开票比例不能为空")
    private BigDecimal unInvoicedRate;

    @NotNull(groups = {CreateGroup.class}, message = "合同基准利率不能为空")
    private BigDecimal contractBaseInterest;

    @NotNull(groups = {CreateGroup.class}, message = "预计回款天数不能为空")
    private Integer expectHKDays;

    @NotNull(groups = {CreateGroup.class}, message = "贸易商加价不能为空")
    private BigDecimal tradeAddPrice;

    @NotNull(groups = {CreateGroup.class}, message = "加权单价不能为空")
    private BigDecimal weightedPrice;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
