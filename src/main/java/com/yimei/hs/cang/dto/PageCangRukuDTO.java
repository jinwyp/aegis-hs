package com.yimei.hs.cang.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.InStorageStatus;
import com.yimei.hs.enums.TrafficMode;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */
@Data
public class PageCangRukuDTO extends BaseFilter<PageCangRukuDTO> {

    private long orderId;
    private long hsId;
    private LocalDateTime rukuDate;
    private InStorageStatus rukuStatus;
    private BigDecimal rukuPrice;
    private String locality;
    private TrafficMode trafficMode;
    private BigDecimal rukuAmount;
}
