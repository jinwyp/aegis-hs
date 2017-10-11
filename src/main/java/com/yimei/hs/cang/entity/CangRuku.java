package com.yimei.hs.cang.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.enums.InStorageStatus;
import com.yimei.hs.enums.TrafficMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.security.acl.Group;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CangRuku implements Serializable {

    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "订单Id不能为空")
    private Long orderId;

    @NotNull(groups = {CreateGroup.class}, message = "核算月Id不能为空")
    private Long hsId;

    @NotNull(groups = {CreateGroup.class}, message = "入库时间不能为空")
    private LocalDateTime rukuDate;

    @NotNull(groups = {CreateGroup.class}, message = "入库状态不能为空")
    private InStorageStatus rukuStatus;

    @NotNull(groups = {CreateGroup.class}, message = "入库数量不能为空")
    private BigDecimal rukuAmount;

    @NotNull(groups = {CreateGroup.class}, message = "入库价格不能为空")
    private BigDecimal rukuPrice;

    @NotNull(groups = {CreateGroup.class}, message = "厂库名称不能为空")
    private String locality;

    @NotNull(groups = {CreateGroup.class}, message = "运输方式不能为空")
    private TrafficMode trafficMode;

    private Integer cars;

    private String jhh;

    private String ship;

    private LocalDateTime tsc;

    private Boolean deleted;

    private static final long serialVersionUID = 1L;

}
