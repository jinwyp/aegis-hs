package com.yimei.hs.same.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.PayMode;
import com.yimei.hs.enums.ReceivePaymentPurpose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
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



}
