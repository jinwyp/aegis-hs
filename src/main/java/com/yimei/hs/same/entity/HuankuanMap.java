package com.yimei.hs.same.entity;

import com.yimei.hs.boot.api.CreateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HuankuanMap implements Serializable {
    private Long id;

    // @NotNull(groups = {CreateGroup.class}, message = "业务线id不能为空")
    private Long orderId;

    private Long jiekuanId;

    @NotNull(groups = {CreateGroup.class}, message = "付款记录不能为空")
    private Long huankuanId;

    @NotNull(groups = {CreateGroup.class}, message = "还款本金不能为空")
    private BigDecimal principal;

    @NotNull(groups = {CreateGroup.class}, message = "还款利息不能为空")
    private BigDecimal interest;

    @NotNull(groups = {CreateGroup.class}, message = "还款服务费不能为空")
    private BigDecimal fee;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
