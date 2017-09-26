package com.yimei.hs.ying.entity;

import com.yimei.hs.enums.FeeClass;
import com.yimei.hs.util.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class YingFee implements Serializable {

    @NotNull(groups = {UpdateGroup.class})
    private Long id;

    private Long orderId;

    private Long hsId;

    private FeeClass name;

    private BigDecimal amount;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;


}