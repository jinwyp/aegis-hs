package com.yimei.hs.ying.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YingHuankuan implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "还款id不能为空")
    private Long id;

    @Max(20)
    @NotNull(groups = {CreateGroup.class}, message = "业务线id不能为空")
    private Long orderId;

    @Max(20)
    @NotNull(groups = {CreateGroup.class}, message = "核算月不能为空")
    private Long hsId;

    @NotNull(groups = {CreateGroup.class}, message = "收款公司不能为空")
    private Long skCompanyId;

    @NotNull(groups = {CreateGroup.class}, message = "还款日期不能为空")
    private LocalDateTime huankuankDate;

    @Null(groups = {UpdateGroup.class}, message = "还款记录不能更新金额")
    private BigDecimal huankuanAmount;

    @NotNull(groups = {CreateGroup.class}, message = "还款利息不能为空")
    private BigDecimal huankuanInterest;

    @NotNull(groups = {CreateGroup.class}, message = "还款服务费不能为空")
    private BigDecimal huankuanFee;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

    private List<YingFukuan> fukuanList;   //  都还了哪些款

    private List<YingHuankuanMap> huankuanMapList;  // 还款明细

}

