package com.yimei.hs.controller;

import com.sun.org.apache.regexp.internal.RE;
import com.yimei.hs.boot.annotation.CurrentUser;
import com.yimei.hs.entity.User;
import com.yimei.hs.entity.dto.Result;
import com.yimei.hs.entity.dto.UserDTO;
import com.yimei.hs.enums.UserRole;
import com.yimei.hs.service.UserService;
import com.yimei.hs.util.BeanMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Created by xiangyang on 2017/7/1.
 */
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserAdminController.class);
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String hello(Model model) {
        model.addAttribute("userName", "张三");
        return "/index";
    }

    /**
     * 注册， 当做添加用户的接口
     * @param user
     * @return
     */
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody @Validated UserDTO user) {
        userService.registerUser(user);
        return new ResponseEntity<Object>("OK", HttpStatus.OK);
    }


    /**
     * jwt
     * 登录，下发Authorization Bearer
     * @param securePhone
     * @param plainPassword
     * @return
     */
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestParam("securePhone") String securePhone, @RequestParam("plainPassword") String plainPassword) {
        User user = userService.loadBySecurePhone(StringUtils.trim(securePhone));
        if (user == null) {
            return new ResponseEntity("userNotExists", HttpStatus.OK);
        } else if (!userService.validPasswordEquals(user.getPhone(), plainPassword)) {
            return new ResponseEntity("passwordError", HttpStatus.OK);
        } else if (!user.getIsActive()) {
            return new ResponseEntity("accountLocked", HttpStatus.OK);
        } else {
            UserDTO userDTO = BeanMapper.map(user, UserDTO.class);
            return new ResponseEntity(userService.genAuthorization(userDTO), HttpStatus.OK);
        }
    }

    /**
     * jwt example
     * @param userDTO
     * @return
     */
    @GetMapping(value = "/authorization")
    public ResponseEntity authorization(@CurrentUser UserDTO userDTO) {
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/users/create")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<UserDTO>> createUser(@RequestBody UserDTO userDTO) {

        logger.warn("id"+userDTO.getId()+userDTO.getPlainPassword()+userDTO.getPlainPassword());
        User user = new User();
        user.setId(1002l);
        user.setCreateBy("ADMIN");
        user.setIsActive(true);
        user.setPassword(userDTO.getPlainPassword());
        user.setRole(UserRole.FINANCIAL_ACCOUNTER);
        user.setDeptId(userDTO.getDeptId());
        user.setPhone(userDTO.getPhone());
        user.setIsAdmin(userDTO.getIsAdmin());
        user.setCreateDate(LocalDateTime.now());
        user.setPasswordSalt("");
        userService.createUser(user);
        userDTO.setId(user.getId());
        if (user.getId() != null) {
            return Result.ok(userDTO);

        } else {
            return Result.error(5003, "字段缺失");
        }
    }



}
