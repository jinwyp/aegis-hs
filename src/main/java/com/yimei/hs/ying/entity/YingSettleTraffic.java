package com.yimei.hs.ying.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YingSettleTraffic implements Serializable {

//    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空")
    private Long id;

//    @NotNull(groups = {CreateGroup.class}, message = "业务线不能为空")
    private Long orderId;

    @NotNull(groups = {CreateGroup.class}, message = "核算月不能为空")
    private Long hsId;

    private LocalDateTime settleDate;

    private BigDecimal amount;

    private BigDecimal money;

    private Long trafficCompanyId;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}