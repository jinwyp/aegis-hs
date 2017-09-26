package com.yimei.hs.user.entity;

import com.yimei.hs.util.CreateGroup;
import com.yimei.hs.util.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
public class Team implements Serializable {

    @NotEmpty(groups = {UpdateGroup.class})
    private Long id;

    @NotEmpty(groups = {CreateGroup.class})
    private String name;

    @NotEmpty(groups = {CreateGroup.class})
    private Long deptId;

    private static final long serialVersionUID = 1L;
}

