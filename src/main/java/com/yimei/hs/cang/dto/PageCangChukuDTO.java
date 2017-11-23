package com.yimei.hs.cang.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.TrafficMode;
import lombok.Data;

import java.math.BigDecimal;
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
    private LocalDateTime chukuDateStart;//  出库起始日期,
    private LocalDateTime chukuDateEnd;//    出库结束日期,

}
