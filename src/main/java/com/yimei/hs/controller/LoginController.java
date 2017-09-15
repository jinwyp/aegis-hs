package com.yimei.hs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by hary on 2017/9/15.
 */
@Controller
public class LoginController {

    @GetMapping("/web/login")
    public String hello(Model model) {
        return "index";
    }
}
