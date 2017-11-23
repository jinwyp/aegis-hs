package com.yimei.hs.same.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
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

public class PageSettleBuyerDTO extends BaseFilter<PageSettleBuyerDTO> {
    private Long orderId;
    private Long hsId;
    private LocalDateTime settleDate;
    private BigDecimal amount;
    private BigDecimal money;
    private BigDecimal settleGap;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate settleDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate settleDateEnd;


}
