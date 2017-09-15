package com.yimei.hs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hary on 2017/9/15.
 */
@Controller
@RequestMapping("/api")
public class TeamController {

    /**
     * 获取所有team
     * @return
     */
    @GetMapping("/teams")
    public String list() {
        return "team";
    }

    /**
     * 获取team
     * @param id
     * @return
     */
    @GetMapping("/team/:id")
    public String read(long id) {
        return "team";
    }

    /**
     * 创建team
     * @return
     */
    @PostMapping("/teams")
    public String create() {
        return "team";
    }

    /**
     *  更新team
     * @return
     */
    @PutMapping("/team/:id")
    public String update() {
        return "team";
    }

}
