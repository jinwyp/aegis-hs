package com.yimei.hs.user.entity;

import com.yimei.hs.boot.api.CreateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "部门不能为空")
    private Long deptId;

    @NotEmpty(groups = {CreateGroup.class}, message="手机号不能为空")
    private String phone;

    @NotEmpty(groups = {ChangePassword.class, CreateGroup.class}, message = "密码不能为空")
    private String password;

    private String passwordSalt;

    private LocalDateTime createDate;

    private String createBy;

    private Boolean isAdmin;

    private Boolean isActive;

    private static final long serialVersionUID = 1L;

    // for change password
    @NotEmpty(groups = {ChangePassword.class}, message = "旧密码必须存在")
    private String oldPassword;

    /**
     * Created by hary on 2017/10/9.
     */
    public static interface ChangePassword {
    }
}

