package com.yimei.hs.ying.entity;

import com.yimei.hs.enums.CustomerType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class YingOrderParty implements Serializable {
    private Long id;

    private Long orderId;

    private CustomerType custType;

    private Long customerId;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
