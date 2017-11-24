package com.yimei.hs.cang.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
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
public class PageCangChukuDTO extends BaseFilter<PageCangChukuDTO> {

    private Long orderId;
    private Long hsId;
    private LocalDateTime chukuDate;
    private BigDecimal chukuPrice;
//    private String locality;
    private BigDecimal chukuAmount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate chukuDateStart;//  出库起始日期,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate chukuDateEnd;//    出库结束日期,

}
