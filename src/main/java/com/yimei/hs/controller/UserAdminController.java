package com.yimei.hs.controller;

import com.yimei.hs.entity.User;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import com.yimei.hs.entity.dto.PageUserDTO;
import com.yimei.hs.service.UserService;
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
     * 获取所有
     *
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity<PageResult<User>> list(PageUserDTO pageUserDTO
    ) {
        return PageResult.ok(userService.loadAllUser(pageUserDTO));
    }

    /**
     * 获取用户信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/users/{phone}")
    public ResponseEntity<Result<User>> read( @PathVariable("phone") String phone) {

        return Result.ok(userService.loadBySecurePhone(phone));
    }

    /**
     * @return
     */
    @PostMapping("/users")
    public ResponseEntity<Result<User>> create(@RequestBody User user) {
        userService.createUser(user);
        return Result.ok(user);
    }

    /**
     * 更新用户
     * @return
     */
    @PutMapping("/users/:id")
    public ResponseEntity<Result<Integer>> update( @PathVariable("id") long id )
    {
        return Result.ok(1);
    }


}
