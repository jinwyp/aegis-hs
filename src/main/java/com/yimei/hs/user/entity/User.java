package com.yimei.hs.user.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    private Long id;

    @NotNull(message = "部门不能为空")
    private Long deptId;

    @NotEmpty(message = "手机号不能为空")
    private String phone;

    @NotEmpty(message = "密码不能为空")
    private String password;

    private String passwordSalt;

    private LocalDateTime createDate;

    private String createBy;

    private Boolean isAdmin;

    private Boolean isActive;

    private static final long serialVersionUID = 1L;
}

