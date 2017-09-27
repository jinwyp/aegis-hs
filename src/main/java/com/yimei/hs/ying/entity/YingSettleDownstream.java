package com.yimei.hs.ying.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class YingSettleDownstream implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "记录id不能为空")
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "业务线id不能为空")
    private Long orderId;

    @NotNull(groups = {CreateGroup.class}, message = "核算月不能为空")
    private Long hsId;

    private LocalDateTime settleDate;

    @Null(groups = {UpdateGroup.class}, message = "下游结算不能更新金额")
    private BigDecimal amount;

    private BigDecimal money;

    private BigDecimal settleGap;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
