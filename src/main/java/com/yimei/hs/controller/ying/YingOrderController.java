package com.yimei.hs.controller.ying;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api")
@RestController
public class YingOrderController {
    // hs_yin_order
    // hs_yin_order_config


    /**
     * 获取所有order
     *
     * @return
     */
    @GetMapping("/orders")
    public String list() {
        return "order";
    }

    /**
     * 获取order
     *
     * @param id
     * @return
     */
    @GetMapping("/order/:id")
    public String read(long id) {
        return "order";
    }

    /**
     * 创建order
     *
     * @return
     */
    @PostMapping("/orders")
    public String create() {
        return "order";
    }

    /**
     * 更新order
     *
     * @return
     */
    @PutMapping("/order/:id")
    public String update() {
        return "order";
    }
}
