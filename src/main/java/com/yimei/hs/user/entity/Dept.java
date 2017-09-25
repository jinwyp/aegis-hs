package com.yimei.hs.user.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Dept implements Serializable {
    private Long id;

    private String name;

    private static final long serialVersionUID = 1L;

}
