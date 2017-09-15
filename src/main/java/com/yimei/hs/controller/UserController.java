package com.yimei.hs.controller;

import com.yimei.hs.boot.annotation.CurrentUser;
import com.yimei.hs.entity.User;
import com.yimei.hs.entity.dto.UserDTO;
import com.yimei.hs.service.UserService;
import com.yimei.hs.util.BeanMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xiangyang on 2017/7/1.
 */
@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String hello(Model model) {
        model.addAttribute("userName", "张三");
        return "/index";
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody @Validated UserDTO user) {
        userService.registerUser(user);
        return new ResponseEntity<Object>("OK", HttpStatus.OK);
    }


    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestParam("phone") String securePhone, @RequestParam("plainPassword") String plainPassword) {
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

    @GetMapping(value = "/authorization")
    public ResponseEntity authorization(@CurrentUser UserDTO userDTO) {
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }
}
