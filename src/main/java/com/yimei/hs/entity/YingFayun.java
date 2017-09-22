package com.yimei.hs.entity;

import com.yimei.hs.enums.CargoArriveStatus;
import com.yimei.hs.enums.TrafficMode;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class YingFayun implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private LocalDateTime fyDate;

    private BigDecimal fyAmount;

    private CargoArriveStatus arriveStatus;

    private TrafficMode upstreamTrafficMode;

    private Integer upstreamCars;

    private String upstreamJHH;

    private String upstreamShip;

    private TrafficMode downstreamTrafficMode;

    private Integer downstreamCars;

    private String downstreamJHH;

    private String downstreamShip;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}