package com.yimei.hs.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Team implements Serializable {
    private Long id;

    private String name;

    private Long deptId;

    private static final long serialVersionUID = 1L;
}

