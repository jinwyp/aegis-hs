package com.yimei.hs.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    private long id;

    private String personalName;

    private String securePhone;

    private boolean isActive;

    private String password;

    private String passwordSalt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}

