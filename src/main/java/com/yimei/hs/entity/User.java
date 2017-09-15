package com.yimei.hs.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    private Long id;

    private Long deptId;

    private String phone;

    private String password;

    private String passwordSalt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    private String createBy;

    private Boolean isAdmin;

    private Boolean isActive;


}
