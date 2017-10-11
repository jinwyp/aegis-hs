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

    private long orderId;
    private long hsId;
    private LocalDateTime chukuDate;
    private BigDecimal chukuPrice;
    private String locality;
    private BigDecimal chukuAmount;

}
