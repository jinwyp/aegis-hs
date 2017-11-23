package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.BailType;
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
public class PageYingBailDTO extends BaseFilter<PageYingBailDTO>{
    private Long orderId;
    private Long hsId;
    private LocalDateTime bailDate;
    private BigDecimal bailAmount;
    private BailType bailType;
    private Long openCompanyId;
    private Long receiverId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bailDateStart;
     @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bailDateEnd;
}
