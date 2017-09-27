package com.yimei.hs.ying.entity;

import com.yimei.hs.boot.api.CreateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YingHuankuanMap implements Serializable {
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "业务线id不能为空")
    private Long orderId;

    private Long huankuanId;

    private Long fukuanId;

    private BigDecimal principal;

    private BigDecimal amount;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
