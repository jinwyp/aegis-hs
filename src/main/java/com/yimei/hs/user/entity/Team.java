package com.yimei.hs.user.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
public class Team implements Serializable {

    @NotEmpty(groups = {UpdateGroup.class}, message = "团队不能为空")
    private Long id;

    @NotEmpty(groups = {CreateGroup.class}, message = "团队名称不能为空")
    private String name;

    @NotEmpty(groups = {CreateGroup.class}, message = "部门不能为空")
    private Long deptId;

    private boolean delete;

    private static final long serialVersionUID = 1L;

}

