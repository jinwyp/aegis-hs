package com.yimei.hs.same.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.ying.entity.YingFukuan;
import com.yimei.hs.ying.entity.YingHuankuanMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Huankuan implements Serializable {

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
    @NotNull(groups = {CreateGroup.class} ,message = "还款总额不能为空")
    private BigDecimal huankuanPrincipal;

    @NotNull(groups = {CreateGroup.class}, message = "还款利息不能为空")
    private BigDecimal huankuanInterest;

    @NotNull(groups = {CreateGroup.class}, message = "还款服务费不能为空")
    private BigDecimal huankuanFee;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private LocalDateTime tsu;

    private static final long serialVersionUID = 1L;

    private List<Jiekuan> jiekuanList;   //  都还了哪些借款

    @NotNull(groups = {CreateGroup.class}, message = "还款明细不能为空")
    @Size(groups = { CreateGroup.class }, min = 1, message = "还款明细不能为空")
    @Valid
    private List<HuankuanMap> huankuanMapList;  // 还款明细

}

