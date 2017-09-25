package com.yimei.hs.ying.entity;

import com.yimei.hs.enums.PayMode;
import com.yimei.hs.enums.RecivedPaymentPurpose;
import lombok.Data;

import javax.validation.constraints.Max;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class YingHuikuan implements Serializable {
    private Long id;
    @Max(20)
    private Long orderId;
    @Max(20)
    private Long hsId;
    @Max(20)
    private Long huikuanCompanyId;

    private LocalDateTime huikuanDate;

    private BigDecimal huikuanAmount;

    private RecivedPaymentPurpose huikuanUsage;

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
