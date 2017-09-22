package com.yimei.hs.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CangOrderParty implements Serializable {
    private Long id;

    private Long orderId;

    private String custType;

    private Long customerId;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
