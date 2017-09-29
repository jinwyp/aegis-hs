package com.yimei.hs.user.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "部门不能为空")
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "部门名称不能为空")
    private String name;

    private static final long serialVersionUID = 1L;
}
