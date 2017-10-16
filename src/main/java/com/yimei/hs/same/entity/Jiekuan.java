package com.yimei.hs.same.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class Jiekuan implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "借款id不能为空")
    private Long id;

    private Long fukuanId;

    @NotNull(groups = {CreateGroup.class}, message = "订单id不能为空")
    private Long orderId;

    @NotNull(groups = {CreateGroup.class}, message = "核算月id不能为空")
    private Long hsId;

    @NotNull(groups = {CreateGroup.class}, message = "借款金额不能为空")
    private BigDecimal amount;

    @NotNull(groups = {CreateGroup.class}, message = "资金方id不能为空")
    private Long capitalId;

    @NotNull(groups = {CreateGroup.class}, message = "资金使用利率不能为空")
    private BigDecimal useInterest;

    @NotNull(groups = {CreateGroup.class}, message = "资金使用天数不能为空")
    private Integer useDays;

    private Boolean deleted;

    @NotNull(groups = {CreateGroup.class}, message = "借款日期不能为空")
    private LocalDateTime jiekuanDate;

    private Date tsc;

    private Date tsu;

    private static final long serialVersionUID = 1L;

    @NotNull(groups = {UpdateGroup.class}, message = "借款对应的还款明细不能为空")
    private List<HuankuanMap> huankuanMapList; //

    @NotNull(groups = {UpdateGroup.class}, message = "借款对应的还款不能为空")
    private List<Huankuan> huankuanList;       // 借款对应的还款列表

    private BigDecimal huankuanTotal;
}