package com.yimei.hs.same.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetail implements Serializable {
    private Long id;

    private Long invoiceId;

//    @NotNull(groups = {CreateGroup.class}, message = "发票号不能为空")
    private String invoiceNumber;

    @NotNull(groups = {CreateGroup.class}, message = "货物数量不能为空")
    private BigDecimal cargoAmount;

    @NotNull(groups = {CreateGroup.class}, message = "税率不能为空")
    private BigDecimal taxRate;

    @NotNull(groups = {CreateGroup.class}, message = "含税价不能为空")
    private BigDecimal priceAndTax;

    private BigDecimal amount;

    private BigDecimal taxAmount;

    private String cargoType;

    private Integer sheetAmount;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
