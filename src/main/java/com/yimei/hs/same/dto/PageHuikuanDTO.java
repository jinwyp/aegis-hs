package com.yimei.hs.same.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.PayMode;
import com.yimei.hs.enums.ReceivePaymentPurpose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)

public class PageHuikuanDTO extends BaseFilter<PageHuikuanDTO> {
    private Long orderId;
    private Long hsId;
    private Long huikuanCompanyId;
    private LocalDateTime huikuanDate;
    private PayMode huikuanMode;
    private BigDecimal huikuanAmount;
    private ReceivePaymentPurpose huikuanUsage;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate huikuanDateStart;    // 回款起始日期,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate  huikuanDateEnd; //    回款终止日期,




}
