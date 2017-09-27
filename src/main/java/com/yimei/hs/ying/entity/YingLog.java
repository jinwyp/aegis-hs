package com.yimei.hs.ying.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YingLog implements Serializable {
    private Long id;


    @NonNull
    private Long orderId;

    @NonNull
    private Long hsId;

    @NonNull
    private Long entityId;

    @NonNull
    private String entityType;

    @NonNull
    private String memo;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
