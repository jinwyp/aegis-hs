package com.yimei.hs.user.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.ext.annotation.CurrentUser;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by xiangyang on 2017/7/1.
 */
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    /**
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/api/register")
    public ResponseEntity<Result<Integer>> register(@RequestBody @Validated User user) {
        int rtn = userService.create(user, null);
        if (rtn != 1) {
            logger.error("用户注册失败: {}", user);
            return Result.error(4001, "创建失败");
        }
        return Result.ok(1);
    }


    /**
     * 登录下发 jwt 和cookie
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/api/login")
    public ResponseEntity<Result<String>> login(@RequestBody User user) {

        User record = userService.getUserByPhone(StringUtils.trim(user.getPhone()));
        if (record == null) {
            return Result.error(4001, "账号不存在", HttpStatus.UNAUTHORIZED);
        } else if (!userService.validPasswordEquals(record, user.getPassword())) {
            return Result.error(4002, "密码错误", HttpStatus.UNAUTHORIZED);
        } else if (!record.getIsActive()) {
            return Result.error(4003, "用户已经禁用", HttpStatus.UNAUTHORIZED);
        } else {
            return Result.ok(userService.genAuthorization(record));
        }



    }

    /**
     * 登出
     *
     * @param user
     * @return
     */
    @GetMapping(value = "/api/logout")
    public ResponseEntity<Result<Integer>> logout(@CurrentUser User user) {
        return Result.ok(1);
    }


    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    @PutMapping(value = "/api/change_password", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Result<Integer>> change(
            @CurrentUser User user,
            @RequestBody @Validated(User.ChangePassword.class) User userUpdate
    ) {
        User record = userService.getUserByPhone(StringUtils.trim(user.getPhone()));
        if (record == null) {
            return Result.error(4001, "账号不存在", HttpStatus.UNAUTHORIZED);
        } else if (!userService.validPasswordEquals(record, userUpdate.getOldPassword())) {
            return Result.error(4002, "密码错误", HttpStatus.BAD_REQUEST);
        } else if (!record.getIsActive()) {
            return Result.error(4003, "用户已经禁用", HttpStatus.UNAUTHORIZED);
        } else {
            userUpdate.setId(user.getId());
            int rtn = userService.changePassword(userUpdate);
            if (rtn != 1) {
                return Result.error(4001, "更新密码失败");
            }
            return Result.ok(1);
        }
    }

    /**
     * jwt example
     *
     * @return
     */
    @GetMapping(value = "/api/user/session")
    public ResponseEntity<Result<User>> authorization(@CurrentUser User user) {
        user.setPassword(null);
        user.setPasswordSalt(null);
        return Result.ok(user);
    }


    /**
     * 获取用户同部门的的用户列表
     *
     * @return
     */
    @GetMapping(value = "/api/user/list_dept_user")
    public ResponseEntity<Result<List<User>>> userList(
            @CurrentUser User user
    ) {
        List<User> userList = userService.getUsersOfSameDept(user);
        return Result.ok(userList);
    }
}
