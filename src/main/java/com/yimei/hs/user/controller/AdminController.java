package com.yimei.hs.user.controller;

import com.yimei.hs.boot.annotation.IsAdmin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hary on 2017/9/25.
 */
@RestController()
@IsAdmin
@RequestMapping("/api/admin")
public class AdminController {
}
