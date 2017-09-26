package com.yimei.hs.ying.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class YingHuankuan implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "还款id不能为空")
    private Long id;

    @Max(20)
    @NotNull(groups = {CreateGroup.class}, message = "业务线id不能为空")
    private Long orderId;

    @Max(20)
    @NotNull(groups = {CreateGroup.class}, message = "核算月不能为空")

    private Long hsId;

    private Long skCompanyId;

    private LocalDateTime huankuankDate;

    @Null(groups = {UpdateGroup.class}, message = "还款记录不能更新金额")
    private BigDecimal huankuanAmount;

    private BigDecimal huankuanInterest;

    private BigDecimal huankuanFee;

    private LocalDateTime tsc;

    private List<YingFukuan> fukuanList;

    private static final long serialVersionUID = 1L;
}

