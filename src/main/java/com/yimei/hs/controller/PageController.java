package com.yimei.hs.controller;

import com.yimei.hs.boot.annotation.CurrentUser;
import com.yimei.hs.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by hary on 2017/9/15.
 */
@Controller
public class PageController {

    @GetMapping("/web/index")
    public String index(@CurrentUser User user) {
        return "/index";
    }

    /**
     * 管理页面 为了前端路由需要重定向
     */
    @GetMapping("/web/index/**")
    public String adminHome() {
        return "forward:/web/index";
    }



    @GetMapping("/web/login")
    public String hello(Model model) {
        return "/login";
    }


}
