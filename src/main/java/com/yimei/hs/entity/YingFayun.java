package com.yimei.hs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class YingFayun implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private Date fyDate;

    private BigDecimal fyAmount;

    private String arriveStatus;

    private String upstreamTrafficMode;

    private Integer upstreamCars;

    private String upstreamJHH;

    private String upstreamShip;

    private String downstreamTrafficMode;

    private Integer downstreamCars;

    private String downstreamJHH;

    private String downstreamShip;

    private Date tsc;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getHsId() {
        return hsId;
    }

    public void setHsId(Long hsId) {
        this.hsId = hsId;
    }

    public Date getFyDate() {
        return fyDate;
    }

    public void setFyDate(Date fyDate) {
        this.fyDate = fyDate;
    }

    public BigDecimal getFyAmount() {
        return fyAmount;
    }

    public void setFyAmount(BigDecimal fyAmount) {
        this.fyAmount = fyAmount;
    }

    public String getArriveStatus() {
        return arriveStatus;
    }

    public void setArriveStatus(String arriveStatus) {
        this.arriveStatus = arriveStatus == null ? null : arriveStatus.trim();
    }

    public String getUpstreamTrafficMode() {
        return upstreamTrafficMode;
    }

    public void setUpstreamTrafficMode(String upstreamTrafficMode) {
        this.upstreamTrafficMode = upstreamTrafficMode == null ? null : upstreamTrafficMode.trim();
    }

    public Integer getUpstreamCars() {
        return upstreamCars;
    }

    public void setUpstreamCars(Integer upstreamCars) {
        this.upstreamCars = upstreamCars;
    }

    public String getUpstreamJHH() {
        return upstreamJHH;
    }

    public void setUpstreamJHH(String upstreamJHH) {
        this.upstreamJHH = upstreamJHH == null ? null : upstreamJHH.trim();
    }

    public String getUpstreamShip() {
        return upstreamShip;
    }

    public void setUpstreamShip(String upstreamShip) {
        this.upstreamShip = upstreamShip == null ? null : upstreamShip.trim();
    }

    public String getDownstreamTrafficMode() {
        return downstreamTrafficMode;
    }

    public void setDownstreamTrafficMode(String downstreamTrafficMode) {
        this.downstreamTrafficMode = downstreamTrafficMode == null ? null : downstreamTrafficMode.trim();
    }

    public Integer getDownstreamCars() {
        return downstreamCars;
    }

    public void setDownstreamCars(Integer downstreamCars) {
        this.downstreamCars = downstreamCars;
    }

    public String getDownstreamJHH() {
        return downstreamJHH;
    }

    public void setDownstreamJHH(String downstreamJHH) {
        this.downstreamJHH = downstreamJHH == null ? null : downstreamJHH.trim();
    }

    public String getDownstreamShip() {
        return downstreamShip;
    }

    public void setDownstreamShip(String downstreamShip) {
        this.downstreamShip = downstreamShip == null ? null : downstreamShip.trim();
    }

    public Date getTsc() {
        return tsc;
    }

    public void setTsc(Date tsc) {
        this.tsc = tsc;
    }
}