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
public class PartyController {

    /**
     * 获取所有party
     * @return
     */
    @GetMapping("/partys")
    public String list() {
        return "party";
    }

    /**
     * 获取party
     * @param id
     * @return
     */
    @GetMapping("/party/:id")
    public String read(long id) {
        return "party";
    }

    /**
     * 创建party
     * @return
     */
    @PostMapping("/partys")
    public String create() {
        return "party";
    }

    /**
     *  更新party
     * @return
     */
    @PutMapping("/party/:id")
    public String update() {
        return "party";
    }

}
