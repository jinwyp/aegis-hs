package com.yimei.hs.same.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.DiscountMode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.ad
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class PageSettleSellerDTO extends BaseFilter<PageSettleSellerDTO> {
    private Long orderId;
    private Long hsId;
    private LocalDateTime settleDate;
    private DiscountMode  discountMode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate settleDateStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate settleDateEnd;

}
