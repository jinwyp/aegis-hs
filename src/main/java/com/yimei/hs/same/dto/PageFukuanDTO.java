package com.yimei.hs.same.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.PayMode;
import com.yimei.hs.enums.PaymentPurpose;
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
public class PageFukuanDTO extends BaseFilter<PageFukuanDTO> {
    private Long orderId;
    private Long hsId;

    private LocalDateTime payDate;
    private Long receiveCompanyId;
    private PaymentPurpose payUsage;
    private PayMode payMode;
    private BigDecimal payAmount;

    // 回款是否完成
    private Boolean huikuanUnfinished = false;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate payDateStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate payDateEnd;
}
