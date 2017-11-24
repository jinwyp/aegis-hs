package com.yimei.hs.cang.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.InStorageStatus;
import com.yimei.hs.enums.TrafficMode;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */
@Data
public class PageCangRukuDTO extends BaseFilter<PageCangRukuDTO> {

    private Long orderId;
    private Long hsId;

    private LocalDateTime rukuDate;
    private InStorageStatus rukuStatus;
    private BigDecimal rukuPrice;
    private String locality;
    private TrafficMode trafficMode;
    private BigDecimal rukuAmount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rukuDateStart;//    : 入库起始日期

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rukuDateEnd;//  : 入库结束日期,'



}
