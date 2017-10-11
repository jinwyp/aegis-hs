package com.yimei.hs.user.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.ext.annotation.CurrentUser;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.user.dto.PageUserDTO;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/25.
 */
@RestController()
@RequestMapping("/api")
@Logined(isAdmin = true)
public class AdminController {

    public static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    UserService userService;

    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    @PostMapping("/users")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<User>> create(
            @CurrentUser User admin,
            @RequestBody @Validated User user
    ) {

        try {
            int rtn = userService.create(user, admin);
            if (rtn != 1) {
                logger.error("创建用户失败: {}", user);
                return Result.error(4001, "创建用户失败");
            } else {
                return Result.ok(user);
            }
        } catch (Exception e) {
            System.out.println("asdasfdasfasdfafds---------------------asdasdfasdasdfasdfasdasad");
            return Result.error(4001, "创建失败-参数非法");
        }

    }


    /**
     * 查询用户, 分页
     *
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity<Result<Page<User>>> list(PageUserDTO pageUserDTO) {
        return Result.ok(userService.getPage(pageUserDTO));
    }


    /**
     * 获取用户
     *
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<Result<User>> read(@PathVariable long id) {
        User nuser = userService.findOne(id);
        if (nuser == null) {
            return Result.error(4001, "记录不存在");
        } else {
            return Result.ok(nuser);
        }
    }

    /**
     * 更新用户信息 - 非密码
     *
     * @return
     */
    @PutMapping("/users/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("id") Long id,
            @RequestBody User user
    ) {
        user.setId(id);
        userService.update(user);
        return Result.ok(1);
    }
}


