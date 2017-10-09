package com.yimei.hs.ying.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.enums.PayMode;
import com.yimei.hs.enums.ReceivePaymentPurpose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YingHuikuan implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空")
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "业务线不能为空")
    private Long orderId;

    @Max(20)
    @NotNull(groups = {CreateGroup.class}, message = "核算月不能为空")
    private Long hsId;

    @NotNull(groups = {CreateGroup.class}, message = "回款公司不能为空")
    private Long huikuanCompanyId;

    @NotNull(groups = {CreateGroup.class}, message = "回款日期不能为空")
    private LocalDateTime huikuanDate;

    @Null(groups = {UpdateGroup.class}, message = "更新回款时不能更新余额")  // 更新回款时不能更新金额
    @NotNull(groups = {CreateGroup.class}, message = "回款金额不能为空")
    private BigDecimal huikuanAmount;

    @NotNull(groups = {CreateGroup.class}, message = "回款用途不能为空")
    private ReceivePaymentPurpose huikuanUsage;

    @NotNull(groups = {CreateGroup.class}, message = "回款方式不能为空")
    private PayMode huikuanMode;

    private Boolean huikuanBankPaper;

    private LocalDateTime huikuanBankPaperDate;

    private Boolean huikuanBankDiscount;

    private BigDecimal huikuanBankDiscountRate;

    private LocalDateTime huikuanBankPaperExpire;

    private Boolean huikuanBusinessPaper;

    private LocalDateTime huikuanBusinessPaperDate;

    private Boolean huikuanBusinessDiscount;

    private BigDecimal huikuanBusinessDiscountRate;

    private LocalDateTime huikuanBusinessPaperExpire;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private LocalDateTime tsu;

    private List<YingFukuan> fukuanList;

    private BigDecimal fukuanTotal;  // 对应掉的付款总额

    private static final long serialVersionUID = 1L;
}
