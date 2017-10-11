package com.yimei.hs.same.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.CargoType;
import com.yimei.hs.enums.OrderStatus;
import com.yimei.hs.enums.SettleMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空")
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "业务线类型不能为空")
    private BusinessType businessType;

    @Null(groups = {CreateGroup.class}, message = "部门编号不需要")
    private Long deptId;

    @NotNull(groups = {CreateGroup.class },message = "团队不能为空")
    private Long teamId;

    private Long creatorId;

    private Long ownerId;

    @NotNull(groups = {CreateGroup.class }, message = "主财务公司不能为空")
    private Long mainAccounting;

    @NotEmpty(groups = {CreateGroup.class }, message = "业务线名称不能为空")
    private String line;

    @NotNull(groups = {CreateGroup.class}, message = "货物类型不能为空")
    private CargoType cargoType;

    @NotNull(groups = {CreateGroup.class }, message = "上游不能为空")
    private Long upstreamId;

    @NotNull(groups = {CreateGroup.class}, message = "上游结算方式不能为空")
    private SettleMode upstreamSettleMode;

    @NotNull(groups = {CreateGroup.class }, message = "下游不能为空")
    private Long downstreamId;

    @NotNull(groups = {CreateGroup.class}, message = "下游结算方式不能为空")
    private SettleMode downstreamSettleMode;

    private OrderStatus status;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private List<OrderConfig> orderConfigList;   // 关联的核算月配置

    @Valid()
    private List<OrderParty> orderPartyList; // 关联的参与方

    private static final long serialVersionUID = 1L;
}