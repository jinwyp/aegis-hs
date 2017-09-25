package com.yimei.hs.cang.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CangTransfer implements Serializable {
    private Long id;

    private Long orderId;

    private Long fromUserId;

    private Long toUserId;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;
}
