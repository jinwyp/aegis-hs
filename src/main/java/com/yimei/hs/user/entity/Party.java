package com.yimei.hs.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Party implements Serializable {

    private Long id;

    private Integer partyType;

    private String name;

    private String shortName;

    private static final long serialVersionUID = 1L;
}

