package com.yimei.hs.same.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.enums.InvoiceDirection;
import com.yimei.hs.enums.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空")
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "业务线不能为空")
    private Long orderId;

    @NotNull(groups = {CreateGroup.class}, message = "核算月不能为空")
    private Long hsId;

    @NotNull(groups = {CreateGroup.class}, message = "发票方向不能为空")
    private InvoiceDirection invoiceDirection;

    @NotNull(groups = {CreateGroup.class}, message = "发票类型不能为空")
    private InvoiceType invoiceType;

    @NotNull(groups = {CreateGroup.class}, message = "开票日期不能为空")
    private LocalDateTime openDate;

    @NotNull(groups = {CreateGroup.class}, message = "开票公司不能为空")
    private Long openCompanyId;

    @NotNull(groups = {CreateGroup.class}, message = "收票单位不能为空")
    private Long receiverId;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    @NotNull(groups = {CreateGroup.class}, message = "缺少发票明细")
    @Size(groups = {CreateGroup.class}, min = 1, message = "缺少发票明细")
    @Valid
    List<InvoiceDetail> details;

}
