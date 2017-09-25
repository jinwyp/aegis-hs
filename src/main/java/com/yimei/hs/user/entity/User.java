package com.yimei.hs.user.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    private Long id;

    private Long deptId;

    private String phone;

    private String password;

    private String passwordSalt;

    private LocalDateTime createDate;

    private String createBy;

    private Boolean isAdmin;

    private Boolean isActive;

    private static final long serialVersionUID = 1L;
}

