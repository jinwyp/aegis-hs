package com.yimei.hs.controller.ying;

import com.yimei.hs.entity.YingOrder;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import com.yimei.hs.service.ying.YingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    YingOrderService yingOrderService;

    /**
     * 获取所有order
     *
     * @return
     */
    @GetMapping("/orders")
    public ResponseEntity<PageResult<YingOrder>> list() {
        return PageResult.ok(yingOrderService.quaryAllOrder());
    }

    /**
     * 获取order
     *
     * @param id
     * @return
     */
    @GetMapping("/orders/:id")
    public ResponseEntity<Result<YingOrder>> read( @PathVariable("id") long id ) {
        return Result.ok(yingOrderService.quaryOrderbyId(id));
    }

    /**
     * 创建order
     *
     * @return
     */
    @PostMapping("/orders")
    public ResponseEntity<Result<YingOrder>> create(@RequestBody YingOrder order) {
        yingOrderService.createOrder(order);
        return Result.ok(order);
    }

    /**
     * 更新order
     *
     * @return
     */
    @PutMapping("/order/:id")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        yingOrderService.updateOrder(new YingOrder());
        return Result.ok(1);
    }
}
