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
@AllArgsConstructor
@NoArgsConstructor

public class SettleBuyer implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "记录id不能为空")
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "业务线id不能为空")
    private Long orderId;

    @NotNull(groups = {CreateGroup.class}, message = "核算月不能为空")
    private Long hsId;

    @NotNull(groups = {CreateGroup.class}, message = "结算日期不能为空")
    private LocalDateTime settleDate;

    private BigDecimal amount;

    @NotNull(groups = {CreateGroup.class}, message = "结算金额不能为空")
    private BigDecimal money;

    private BigDecimal settleGap;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private LocalDateTime tsu;

    private static final long serialVersionUID = 1L;

}
