package com.yimei.hs.controller.ying;

import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api")
@RestController
public class YingFayunController {

    /**
     * 获取所有fayun
     *
     * @return
     */
    @GetMapping("/fayuns")
    public String list() {
        return "fayun";
    }

    /**
     * 获取fayun
     *
     * @param id
     * @return
     */
    @GetMapping("/fayun/:id")
    public String read(long id) {
        return "fayun";
    }

    /**
     * 创建fayun
     *
     * @return
     */
    @PostMapping("/fayuns")
    public String create() {
        return "fayun";
    }

    /**
     * 更新fayun
     *
     * @return
     */
    @PutMapping("/fayun/:id")
    public String update() {
        return "fayun";
    }
}
