package com.yimei.hs.ying.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.enums.BailType;
import com.yimei.hs.enums.CargoArriveStatus;
import com.yimei.hs.enums.TrafficMode;
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
public class YingBail implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空")
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "业务线id不能为空")
    private Long orderId;

    @NotNull(groups = {CreateGroup.class}, message = "核算月不能为空")
    private Long hsId;

    @NotNull(groups = {CreateGroup.class}, message = "发运日期不能为空")
    private LocalDateTime bailDate;

    @Null(groups = {UpdateGroup.class}, message = "发运吨数不能更新")
    private BigDecimal bailAmount;

    @NotNull(groups = {CreateGroup.class}, message = "保证金类型不能为空")
    private BailType bailType;

    @NotNull(groups = {CreateGroup.class}, message = "开票单位不能为空")
    private Long openCompanyId;
    @NotNull(groups = {CreateGroup.class}, message = "收票单位不能为空")
    private Long receiverId;


    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private LocalDateTime tsu;

    private static final long serialVersionUID = 1L;


}