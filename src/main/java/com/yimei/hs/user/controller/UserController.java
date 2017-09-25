package com.yimei.hs.user.controller;

import com.yimei.hs.boot.PageResult;
import com.yimei.hs.boot.Result;
import com.yimei.hs.boot.annotation.CurrentUser;
import com.yimei.hs.user.dto.PageUserDTO;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by xiangyang on 2017/7/1.
 */
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    /**
     * 查询用户, 分页
     *
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity<PageResult<User>> list(PageUserDTO pageUserDTO) {
        return PageResult.ok(userService.getPage(pageUserDTO));
    }

//    @RequestMapping("/")
//    public String hello(Model model) {
//        model.addAttribute("userName", "张三");
//        return "/index";
//    }

    /**
     * 注册， 当做添加用户的接口
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/register")
    public ResponseEntity<Result<Boolean>> register(@RequestBody @Validated User user) {
        userService.register(user);
        return Result.ok(true);
    }


    /**
     * 登录下发 jwt 和cookie
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/login")
    public ResponseEntity<Result<String>> login(@RequestBody User user) {
        User record = userService.getUserByPhone(StringUtils.trim(user.getPhone()));
        if (record == null) {
            return Result.error(4001, "账号不存在", HttpStatus.NOT_FOUND);
        } else if (!userService.validPasswordEquals(record, user.getPassword())) {
            return Result.error(4002, "密码错误", HttpStatus.UNAUTHORIZED);
        } else if (!record.getIsActive()) {
            return Result.error(4003, "用户已经禁用", HttpStatus.UNAUTHORIZED);
        } else {
            return Result.ok(userService.genAuthorization(record));
        }
    }

    /**
     * 更新用户信息 - 非密码
     *
     * @return
     */
    @PutMapping("/users")
    public ResponseEntity<Result<Integer>> update(@RequestBody User user) {
        userService.update(user);
        return Result.ok(1);
    }

    /**
     * jwt example
     *
     * @return
     */
    @GetMapping(value = "/authorization")
    public ResponseEntity authorization(@CurrentUser User user) {
        return new ResponseEntity(user, HttpStatus.OK);
    }


}
