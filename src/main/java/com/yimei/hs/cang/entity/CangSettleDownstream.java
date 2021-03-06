package com.yimei.hs.cang.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.enums.DiscountMode;
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
public class CangSettleDownstream implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空")
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "业务线不能为空")
    private Long orderId;

    @NotNull(groups = {CreateGroup.class}, message = "核算月不能为空")
    private Long hsId;

    @NotNull(groups = {CreateGroup.class}, message = "结算日期不能为空")
    private LocalDateTime settleDate;

    @NotNull(groups = {CreateGroup.class}, message = "结算吨数不能为空")
    private BigDecimal amount;

    @NotNull(groups = {CreateGroup.class}, message = "结算金额不能为空")
    private BigDecimal money;

    @NotNull(groups = {CreateGroup.class}, message = "折扣类型不能为空")
    private DiscountMode discountType;

    private BigDecimal discountInterest;

    private Integer discountDays;

    private BigDecimal discountAmount;

    private boolean deleted;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;


}