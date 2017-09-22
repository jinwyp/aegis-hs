package com.yimei.hs.entity;

import lombok.Data;

import javax.validation.constraints.Max;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class YingHuankuan implements Serializable {
    private Long id;
    @Max(20)
    private Long orderId;
    @Max(20)
    private Long hsId;

    private Long skCompanyId;

    private LocalDateTime huankuankDate;

    private BigDecimal huankuanAmount;

    private LocalDateTime tsc;

    private List<YingFukuan> fukuanList;

    public List<YingFukuan> getFukuanList() {
        return fukuanList;
    }

    public void setFukuanList(List<YingFukuan> fukuanList) {
        this.fukuanList = fukuanList;
    }

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

    public Long getSkCompanyId() {
        return skCompanyId;
    }

    public void setSkCompanyId(Long skCompanyId) {
        this.skCompanyId = skCompanyId;
    }

    public LocalDateTime getHuankuankDate() {
        return huankuankDate;
    }

    public void setHuankuankDate(LocalDateTime huankuankDate) {
        this.huankuankDate = huankuankDate;
    }

    public BigDecimal getHuankuanAmount() {
        return huankuanAmount;
    }

    public void setHuankuanAmount(BigDecimal huankuanAmount) {
        this.huankuanAmount = huankuanAmount;
    }

    public LocalDateTime getTsc() {
        return tsc;
    }

    public void setTsc(LocalDateTime tsc) {
        this.tsc = tsc;
    }
}