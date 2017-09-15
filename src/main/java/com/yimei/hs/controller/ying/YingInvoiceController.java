package com.yimei.hs.controller.ying;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api")
@RestController
public class YingInvoiceController {

    /**
     * 获取所有invoice
     *
     * @return
     */
    @GetMapping("/invoices")
    public String list() {
        return "invoice";
    }

    /**
     * 获取invoice
     *
     * @param id
     * @return
     */
    @GetMapping("/invoice/:id")
    public String read(long id) {
        return "invoice";
    }

    /**
     * 创建invoice
     *
     * @return
     */
    @PostMapping("/invoices")
    public String create() {
        return "invoice";
    }

    /**
     * 更新invoice
     *
     * @return
     */
    @PutMapping("/invoice/:id")
    public String update() {
        return "invoice";
    }

}
