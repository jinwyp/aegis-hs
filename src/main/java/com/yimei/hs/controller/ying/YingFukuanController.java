package com.yimei.hs.controller.ying;

import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api")
@RestController
public class YingFukuanController {

    /**
     * 获取所有huankuan
     *
     * @return
     */
    @GetMapping("/huankuans")
    public String list() {
        return "huankuan";
    }

    /**
     * 获取huankuan
     *
     * @param id
     * @return
     */
    @GetMapping("/huankuan/:id")
    public String read(long id) {
        return "huankuan";
    }

    /**
     * 创建huankuan
     *
     * @return
     */
    @PostMapping("/huankuans")
    public String create() {
        return "huankuan";
    }

    /**
     * 更新huankuan
     *
     * @return
     */
    @PutMapping("/huankuan/:id")
    public String update() {
        return "huankuan";
    }
}
