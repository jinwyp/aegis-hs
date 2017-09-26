package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.annotation.CurrentUser;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.ying.entity.YingOrder;
import com.yimei.hs.boot.PageResult;
import com.yimei.hs.boot.Result;
import com.yimei.hs.ying.dto.PageYingOrderDTO;
import com.yimei.hs.enums.OrderStatus;
import com.yimei.hs.ying.service.YingOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/yings")
@RestController
public class YingOrderController {

    private static final Logger logger = LoggerFactory.getLogger(YingOrderController.class);

    @Autowired
    private YingOrderService yingOrderService;

    /**
     * 获取订单分页数据
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<PageResult<YingOrder>> list(PageYingOrderDTO pageYingOrderDTO) {
        logger.info("page ying order args: {}", pageYingOrderDTO);
        return PageResult.ok(yingOrderService.getPage(pageYingOrderDTO));
    }

    /**
     * 获取订单
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result<YingOrder>> read(@PathVariable("id") long id) {
        YingOrder order = yingOrderService.findOne(id);
        if (order == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(order);
        }
    }

    /**
     * 创建order
     *
     * @return
     */
    @PostMapping
    @Transactional(readOnly = false)
    public ResponseEntity<Result<YingOrder>> create(@CurrentUser User user, @RequestBody YingOrder order) {
        order.setCreatorId(user.getId());
        order.setOwnerId(user.getId());
        order.setDeptId(user.getDeptId());
        order.setStatus(OrderStatus.UNCOMPLETED);
        logger.info("my order = {}", order);
        yingOrderService.create(order);
        return Result.ok(order);
    }


    /**
     * 更新order
     *
     * @return
     */
    @PutMapping("/{id}")
    @Transactional(readOnly =  false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("id") long id,
            @RequestBody YingOrder yingOrder
    ) {
        yingOrder.setId(id);
        int rtn = yingOrderService.update(yingOrder);
        logger.error("yingOrder"+yingOrder);
        if (rtn != 1) {
            return Result.error(4001, "更新失败");
        }
        return Result.ok(1);
    }
}
