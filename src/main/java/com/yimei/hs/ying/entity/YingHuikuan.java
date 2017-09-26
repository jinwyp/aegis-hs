package com.yimei.hs.ying.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.enums.PayMode;
import com.yimei.hs.enums.ReceivePaymentPurpose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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

    @Max(20)
    private Long orderId;

    @Max(20)
    @NotNull(groups = {CreateGroup.class}, message = "核算月不能为空")
    private Long hsId;
    @Max(20)
    private Long huikuanCompanyId;

    private LocalDateTime huikuanDate;


    @Null(groups = {UpdateGroup.class})  // 更新回款时不能更新金额
    private BigDecimal huikuanAmount;

    private ReceivePaymentPurpose huikuanUsage;

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

    private LocalDateTime tsc;

    private List<YingFukuan> fukuanList;

    private static final long serialVersionUID = 1L;
}
