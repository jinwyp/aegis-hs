package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.CargoArriveStatus;
import com.yimei.hs.enums.TrafficMode;
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
public class PageYingFayunDTO extends BaseFilter<PageYingFayunDTO>{
    private Long orderId;
    private Long hsId;
    private LocalDateTime fyDate;
    private BigDecimal fyAmount;
    private CargoArriveStatus arriveStatus;
    private TrafficMode upstreamTrafficMode;
    private TrafficMode downstreamTrafficMode;
    private Integer upstreamCars;
    private String upstreamJHH;
    private String  upstreamShip;
    private Integer downstreamCars;
    private String downstreamJHH;
    private String downstreamShip;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fyDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fyDateEnd;
}
