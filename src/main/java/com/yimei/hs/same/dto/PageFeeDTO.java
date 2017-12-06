package com.yimei.hs.same.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.FeeClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by hary on 2017/9/21.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class PageFeeDTO extends BaseFilter<PageFeeDTO> {

    private Long orderId;

    private Long hsId;

    private FeeClass name;

    private BigDecimal amount;

    private BigDecimal quantity;

    private Long otherPartyId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate settleDate;

}
