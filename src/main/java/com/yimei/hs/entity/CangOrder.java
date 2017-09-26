package com.yimei.hs.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CangOrder implements Serializable {
    private Long id;

    private Long deptId;

    private Long teamId;

    private Long creatorId;

    private Long ownerId;

    private Long mainAccounting;

    private String line;

    private String cargoType;

    private Long upstreamId;

    private String upstreamSettleMode;

    private Long downstreamId;

    private String downstreamSettleMode;

    private String status;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getMainAccounting() {
        return mainAccounting;
    }

    public void setMainAccounting(Long mainAccounting) {
        this.mainAccounting = mainAccounting;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line == null ? null : line.trim();
    }

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType == null ? null : cargoType.trim();
    }

    public Long getUpstreamId() {
        return upstreamId;
    }

    public void setUpstreamId(Long upstreamId) {
        this.upstreamId = upstreamId;
    }

    public String getUpstreamSettleMode() {
        return upstreamSettleMode;
    }

    public void setUpstreamSettleMode(String upstreamSettleMode) {
        this.upstreamSettleMode = upstreamSettleMode == null ? null : upstreamSettleMode.trim();
    }

    public Long getDownstreamId() {
        return downstreamId;
    }

    public void setDownstreamId(Long downstreamId) {
        this.downstreamId = downstreamId;
    }

    public String getDownstreamSettleMode() {
        return downstreamSettleMode;
    }

    public void setDownstreamSettleMode(String downstreamSettleMode) {
        this.downstreamSettleMode = downstreamSettleMode == null ? null : downstreamSettleMode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public LocalDateTime getTsc() {
        return tsc;
    }

    public void setTsc(LocalDateTime tsc) {
        this.tsc = tsc;
    }
}