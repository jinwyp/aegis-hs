package com.yimei.hs.controller.ying;

import com.yimei.hs.entity.YingOrder;
import com.yimei.hs.service.ying.YingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api")
@RestController
public class YingOrderController {
    // hs_yin_order
    // hs_yin_order_config


    @Autowired
    YingOrderService mYingOrderService;
    /**
     * 获取所有order
     *
     * @return
     */
    @GetMapping("/orders")
    public String list()
    {

        mYingOrderService.quaryAllOrder();
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
        mYingOrderService.quaryOrderbyId(id);
        return "order";
    }

    /**
     * 创建order
     *
     * @return
     */
    @PostMapping("/orders")
    @Transactional(readOnly = false)
    public String create()
    {
        mYingOrderService.createOrder(new YingOrder());
        return "order";
    }

    /**
     * 更新order
     *
     * @return
     */
    @PutMapping("/order/:id")
    public String update() {
        mYingOrderService.updateOrder(new YingOrder());
        return "order";
    }
}
