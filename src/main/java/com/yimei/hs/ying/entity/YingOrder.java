package com.yimei.hs.ying.entity;

import com.yimei.hs.enums.CargoType;
import com.yimei.hs.enums.OrderStatus;
import com.yimei.hs.enums.SettleMode;
import com.yimei.hs.boot.api.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class YingOrder implements Serializable {


    @NotNull(groups = {UpdateGroup.class})
    private Long id;

    @NotNull(message = "部门编号不能为空")
    private Long deptId;

    @NotNull(message = "团队不能为空")
    private Long teamId;

    private Long creatorId;

    private Long ownerId;

    @NotNull(message = "主财务公司不能为空")
    private Long mainAccounting;

    @NotEmpty(message = "业务线名称不能为空")
    private String line;

    private CargoType cargoType;

    @NotNull(message = "上游不能为空")
    private Long upstreamId;

    private SettleMode upstreamSettleMode;

    @NotNull(message = "下游不能为空")
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