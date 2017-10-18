package com.yimei.hs.same.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.enums.CustomerType;
import com.yimei.hs.user.entity.Party;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderParty implements Serializable {

    private Long id;

    private Long orderId;

    private String name;

    @NotNull(groups = {CreateGroup.class}, message = "客户类型不能为空")
    private CustomerType custType;

    @NotNull(groups = {CreateGroup.class}, message = "客户id不能为空")
    private Long customerId;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private Party party;

    private static final long serialVersionUID = 1L;

}
