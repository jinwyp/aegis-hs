package com.yimei.hs.user.controller;

import com.yimei.hs.user.entity.User;
import com.yimei.hs.boot.PageResult;
import com.yimei.hs.boot.Result;
import com.yimei.hs.user.dto.PageUserDTO;
import com.yimei.hs.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/21.
 */
@RestController
@RequestMapping("/admin")
public class UserAdminController {

    private static final Logger logger = LoggerFactory.getLogger(UserAdminController.class);

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

    /**
     * 获取用户信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/users/{phone}")
    public ResponseEntity<Result<User>> read( @PathVariable("phone") String phone) {
        return Result.ok(userService.getUserByPhone(phone));
    }

    /**
     * @return
     */
    @PostMapping("/users")
    public ResponseEntity<Result<User>> create(@RequestBody User user) {
        userService.register(user);
        return Result.ok(user);
    }

    /**
     * 更新用户
     * @return
     */
    @PutMapping("/users")
    public ResponseEntity<Result<Integer>> update(@RequestBody User user) {
        userService.update(user);
        return Result.ok(1);
    }


}
