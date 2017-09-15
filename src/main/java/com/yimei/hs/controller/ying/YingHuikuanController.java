package com.yimei.hs.controller.ying;

import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api")
@RestController
public class YingHuikuanController {

    /**
     * 获取所有huikuan
     *
     * @return
     */
    @GetMapping("/huikuans")
    public String list() {
        return "huikuan";
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/huikuan/:id")
    public String read(long id) {
        return "huikuan";
    }

    /**
     * 创建huikuan
     *
     * @return
     */
    @PostMapping("/huikuans")
    public String create() {
        return "huikuan";
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @PutMapping("/huikuan/:id")
    public String update() {
        return "huikuan";
    }
}
