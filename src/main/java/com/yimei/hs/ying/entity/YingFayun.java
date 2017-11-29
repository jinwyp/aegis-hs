package com.yimei.hs.ying.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.enums.CargoArriveStatus;
import com.yimei.hs.enums.TrafficMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YingFayun implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空")
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "业务线id不能为空")
    private Long orderId;

    @NotNull(groups = {CreateGroup.class}, message = "核算月不能为空")
    private Long hsId;

    @NotNull(groups = {CreateGroup.class}, message = "发运日期不能为空")
    private LocalDateTime fyDate;

    @DecimalMin(value = "0",message = "发运吨数不能小于0" )
    @DecimalMax(value = "9999999.99")
    private BigDecimal fyAmount;

    @NotNull(groups = {CreateGroup.class}, message = "到场状态不能为空")
    private CargoArriveStatus arriveStatus;

    @NotNull(groups = {CreateGroup.class}, message = "上游运输方式不能为空")
    private TrafficMode upstreamTrafficMode;

    private Integer upstreamCars;

    private String upstreamJHH;

    private String upstreamShip;

    @NotNull(groups = {CreateGroup.class}, message = "下游运输方式不能为空")
    private TrafficMode downstreamTrafficMode;

    private Integer downstreamCars;

    private String downstreamJHH;

    private String downstreamShip;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}