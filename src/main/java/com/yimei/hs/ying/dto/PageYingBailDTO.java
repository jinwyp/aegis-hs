package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.BailType;
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
public class PageYingBailDTO extends BaseFilter<PageYingBailDTO>{
    private Long orderId;
    private Long hsId;
    private LocalDateTime bailDate;
    private BigDecimal bailAmount;
    private BailType bailType;
}
