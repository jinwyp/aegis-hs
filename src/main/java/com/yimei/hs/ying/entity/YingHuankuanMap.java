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

    @NotNull(groups = {CreateGroup.class}, message = "付款记录不能为空")
    private Long fukuanId;

    @NotNull(groups = {CreateGroup.class}, message = "还款本金不能为空")
    private BigDecimal principal;

    @NotNull(groups = {CreateGroup.class}, message = "还款利息不能为空")
    private BigDecimal interest;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
