package com.yimei.hs.same.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
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

    @NotNull(groups = {CreateGroup.class}, message = "还款日期不能为空")
    private LocalDateTime huankuankDate;

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

