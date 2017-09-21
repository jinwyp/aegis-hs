package com.yimei.hs.controller.ying;

import com.yimei.hs.service.ying.YingSettleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api")
@RestController
public class YingSettleController {



    @Autowired
    YingSettleService yingSettleService;

    /**
     * 获取上游所有settle
     *
     * @return
     */
    @GetMapping("/settles")
    public String upStreamlist(@RequestParam Long orderId) {

        yingSettleService.getAllUpStreamSettles(orderId);
        return "settle";
    }

    /**
     * 获取settle
     *
     * @param id
     * @return
     */
    @GetMapping("/settle/:id")
    public String upStreamlread(long id) {
        return "settle";
    }

    /**
     * 创建settle
     *
     * @return
     */
    @PostMapping("/settles")
    public String upStreamlcreate() {
        return "settle";
    }

    /**
     * 更新settle
     *
     * @return
     */
    @PutMapping("/settle/:id")
    public String upStreamUpdate() {
        return "settle";
    }


    @GetMapping("/downsettle/list")
    public String downStreamlist()
    {

//        yingSettleService.getAllSettles
        return "settle";
    }

    /**
     * 获取settle
     *
     * @param id
     * @return
     */
    @GetMapping("/dwonsettle/:id")
    public String downStreamread(long id) {
        return "settle";
    }

    /**
     * 创建settle
     *
     * @return
     */
    @PostMapping("/dwonsettles")
    public String downStreamcreate() {
        return "settle";
    }

    /**
     * 更新settle
     *
     * @return
     */
    @PutMapping("/downStreamettle/:id")
    public String downStreamUpdate() {
        return "settle";
    }
}
