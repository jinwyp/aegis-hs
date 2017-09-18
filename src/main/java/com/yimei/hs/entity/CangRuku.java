package com.yimei.hs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CangRuku implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private LocalDateTime rukuDate;

    private Byte rukuStatus;

    private BigDecimal rukuAmount;

    private BigDecimal rukuPrice;

    private String locality;

    private String trafficMode;

    private Integer cars;

    private String jhh;

    private String ship;

    private LocalDateTime tsc;

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

    public LocalDateTime getRukuDate() {
        return rukuDate;
    }

    public void setRukuDate(LocalDateTime rukuDate) {
        this.rukuDate = rukuDate;
    }

    public Byte getRukuStatus() {
        return rukuStatus;
    }

    public void setRukuStatus(Byte rukuStatus) {
        this.rukuStatus = rukuStatus;
    }

    public BigDecimal getRukuAmount() {
        return rukuAmount;
    }

    public void setRukuAmount(BigDecimal rukuAmount) {
        this.rukuAmount = rukuAmount;
    }

    public BigDecimal getRukuPrice() {
        return rukuPrice;
    }

    public void setRukuPrice(BigDecimal rukuPrice) {
        this.rukuPrice = rukuPrice;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality == null ? null : locality.trim();
    }

    public String getTrafficMode() {
        return trafficMode;
    }

    public void setTrafficMode(String trafficMode) {
        this.trafficMode = trafficMode == null ? null : trafficMode.trim();
    }

    public Integer getCars() {
        return cars;
    }

    public void setCars(Integer cars) {
        this.cars = cars;
    }

    public String getJhh() {
        return jhh;
    }

    public void setJhh(String jhh) {
        this.jhh = jhh == null ? null : jhh.trim();
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship == null ? null : ship.trim();
    }

    public LocalDateTime getTsc() {
        return tsc;
    }

    public void setTsc(LocalDateTime tsc) {
        this.tsc = tsc;
    }
}