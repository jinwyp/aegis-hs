package com.yimei.hs.ying.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class YingInvoiceDetail implements Serializable {
    private Long id;

    private Long invoiceId;

    private String invoiceNumber;

    private BigDecimal cargoAmount;

    private BigDecimal taxRate;

    private BigDecimal priceAndTax;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
