package com.yimei.hs.cang.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class CangLog implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private Long entityId;

    private String entityType;

    private String memo;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}