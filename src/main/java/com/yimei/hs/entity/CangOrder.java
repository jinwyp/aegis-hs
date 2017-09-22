package com.yimei.hs.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
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

}
