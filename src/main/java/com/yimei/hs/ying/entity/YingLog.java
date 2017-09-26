package com.yimei.hs.ying.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YingLog implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private Long entityId;

    private String entityType;

    private String memo;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
