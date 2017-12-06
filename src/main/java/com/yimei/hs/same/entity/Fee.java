package com.yimei.hs.same.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.enums.FeeClass;
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
public class Fee implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空")
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "业务线不能为空")
    private Long orderId;

    @NotNull(groups = {CreateGroup.class}, message = "核算月不能为空")
    private Long hsId;

    @NotNull(groups = {CreateGroup.class}, message = "费用科目不能为空")
    private FeeClass name;

    @NotNull(groups = {CreateGroup.class}, message = "金额不能为空")
    private BigDecimal amount;

    @NotNull(groups = {CreateGroup.class,UpdateGroup.class}, message = "对方不能为空")
    private Long otherPartyId;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 结算日期
     */
    @NotNull(groups = {CreateGroup.class}, message = "结算日期不能为空")
    private LocalDateTime settleDate;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;


}