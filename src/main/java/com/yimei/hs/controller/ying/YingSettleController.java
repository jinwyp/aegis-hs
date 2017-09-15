package com.yimei.hs.controller.ying;

import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api")
@RestController
public class YingSettleController {

    /**
     * 获取所有settle
     *
     * @return
     */
    @GetMapping("/settles")
    public String list() {
        return "settle";
    }

    /**
     * 获取settle
     *
     * @param id
     * @return
     */
    @GetMapping("/settle/:id")
    public String read(long id) {
        return "settle";
    }

    /**
     * 创建settle
     *
     * @return
     */
    @PostMapping("/settles")
    public String create() {
        return "settle";
    }

    /**
     * 更新settle
     *
     * @return
     */
    @PutMapping("/settle/:id")
    public String update() {
        return "settle";
    }
}
