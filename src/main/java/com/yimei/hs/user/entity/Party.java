package com.yimei.hs.user.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class Party implements Serializable {
    private Long id;

    private Integer partyType;

    private String name;

    private String shortName;

    private static final long serialVersionUID = 1L;
}

