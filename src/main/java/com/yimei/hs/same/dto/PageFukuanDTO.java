package com.yimei.hs.same.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.PaymentPurpose;
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
public class PageFukuanDTO extends BaseFilter<PageFukuanDTO> {
    private Long orderId;
    private Long hsId;

    private LocalDateTime payDate;
    private Long receiveCompanyId;
    private PaymentPurpose paymentPurpose;
    private BigDecimal payAmount;

    // 回款是否完成
    private Boolean huikuanUnfinished = false;
}
