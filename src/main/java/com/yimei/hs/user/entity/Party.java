package com.yimei.hs.user.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Party implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "客户不能为空")
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "客户类型不能为空")
    private Integer partyType;

    @NotNull(groups = {CreateGroup.class}, message = "客户名称不能为空")
    private String name;

    @NotNull(groups = {CreateGroup.class}, message = "客户简称不能为空")
    private String shortName;

    private static final long serialVersionUID = 1L;
}

