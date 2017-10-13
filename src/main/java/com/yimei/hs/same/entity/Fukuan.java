package com.yimei.hs.same.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.enums.PaymentPurpose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fukuan implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空")
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "业务线不能为空")
    private Long orderId;

    @NotNull(groups = {CreateGroup.class}, message = "核算月不能为空")
    private Long hsId;

    @NotNull(groups = {CreateGroup.class}, message = "付款日期不能为空")
    @Null(groups = {UpdateGroup.class}, message = "付款日期不能更新")
    private LocalDateTime payDate;

    @NotNull(groups = {CreateGroup.class}, message = "收款公司不能为空")
    private Long receiveCompanyId;

    @NotNull(groups = {CreateGroup.class}, message = "付款用途不能为空")
    private PaymentPurpose payUsage;

    @Null(groups = {UpdateGroup.class}, message = "付款金额不能更新")
    private BigDecimal payAmount;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private LocalDateTime tsu;


    @NotNull(groups = {CreateGroup.class}, message = "资金方id不能为空")
    private Long capitalId;

    private static final long serialVersionUID = 1L;

    // 关联的回款记录
    private List<Huikuan> huikuanList;

    // 所关联的回款关联记录
    private List<HuikuanMap> huikuanMap;
    private BigDecimal huikuanTotal;

    private Jiekuan jiekuan; //当资金方为非自由资金时 此字段不能为空
}
