package com.yimei.hs.ying.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.enums.CargoType;
import com.yimei.hs.enums.OrderStatus;
import com.yimei.hs.enums.SettleMode;
import com.yimei.hs.boot.api.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YingOrder implements Serializable {


    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空")
    private Long id;

    @NotNull(message = "部门编号不能为空")
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

    private SettleMode upstreamSettleMode;

    @NotNull(groups = {CreateGroup.class }, message = "下游不能为空")
    private Long downstreamId;

    private SettleMode downstreamSettleMode;

    private OrderStatus status;

    private LocalDateTime tsc;

    @Valid()
    private List<YingOrderConfig> orderConfigList;   // 关联的核算月配置

    @Valid()
    private List<YingOrderParty> orderPartyList; // 关联的参与方

    private static final long serialVersionUID = 1L;
}