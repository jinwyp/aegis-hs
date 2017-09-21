package com.yimei.hs.controller;

import com.yimei.hs.entity.User;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
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
    public ResponseEntity<PageResult<User>> list(
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNum") int pageNum
    ) {
        return PageResult.ok(null);
    }

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/users/:id")
    public ResponseEntity<Result<User>> read( @PathVariable("id") long id) {
        return Result.ok(null);
    }

    /**
     * @return
     */
    @PostMapping("/users")
    public ResponseEntity<Result<User>> create(@RequestBody User user) {
        return Result.ok(null);
    }

    /**
     * 更新用户
     * @return
     */
    @PutMapping("/users/:id")
    public ResponseEntity<Result<Integer>> update( @PathVariable("id") long id ) {
        return Result.ok(1);
    }


}
