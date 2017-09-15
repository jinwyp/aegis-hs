package com.yimei.hs.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RestController
@RequestMapping("/api")
public class DepartmentController {

    /**
     * 获取所有department
     *
     * @return
     */
    @GetMapping("/departments")
    public String list() {
        return "department";
    }

    /**
     * 获取department
     *
     * @param id
     * @return
     */
    @GetMapping("/department/:id")
    public String read(long id) {
        return "department";
    }

    /**
     * 创建department
     *
     * @return
     */
    @PostMapping("/departments")
    public String create() {
        return "department";
    }

    /**
     * 更新department
     *
     * @return
     */
    @PutMapping("/department/:id")
    public String update() {
        return "department";
    }
}
