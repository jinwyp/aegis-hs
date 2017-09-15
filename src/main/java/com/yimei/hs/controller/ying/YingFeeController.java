package com.yimei.hs.controller.ying;

import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api")
@RestController
public class YingFeeController {

    /**
     * 获取所有fee
     *
     * @return
     */
    @GetMapping("/fees")
    public String list() {
        return "fee";
    }

    /**
     * 获取fee
     *
     * @param id
     * @return
     */
    @GetMapping("/fee/:id")
    public String read(long id) {
        return "fee";
    }

    /**
     * 创建fee
     *
     * @return
     */
    @PostMapping("/fees")
    public String create() {
        return "fee";
    }

    /**
     * 更新fee
     *
     * @return
     */
    @PutMapping("/fee/:id")
    public String update() {
        return "fee";
    }
}
