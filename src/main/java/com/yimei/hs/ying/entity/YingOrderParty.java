package com.yimei.hs.ying.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.xml.ws.soap.Addressing;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YingOrderParty implements Serializable {

    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "业务线不能为空")
    private Long orderId;

    private CustomerType custType;

    private Long customerId;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
