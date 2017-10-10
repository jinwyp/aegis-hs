package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.CurrentUser;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.OrderStatus;
import com.yimei.hs.same.dto.PageOrderDTO;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.service.OrderService;
import com.yimei.hs.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/business/{businessType}s")
@RestController
@Logined
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    /**
     * 获取订单分页数据
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<Result<Page<Order>>> list(
            @CurrentUser User user,
            @PathVariable("businessType") BusinessType businessType,
            PageOrderDTO pageOrderDTO
    ) {
        // 如果不是管理员， 就只展示自己的订单
        if (!user.getIsAdmin()) {
            pageOrderDTO.setOwnerId(user.getId());
        }

        return Result.ok(orderService.getPage(pageOrderDTO));
    }

    /**
     * 获取订单
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result<Order>> read(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("id") long id
    ) {
        Order order = orderService.findOne(id);
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
    public ResponseEntity<Result<Order>> create(
            @CurrentUser User user,
            @PathVariable("businessType") BusinessType businessType,
            @RequestBody @Validated(CreateGroup.class) Order order) {
        order.setCreatorId(user.getId());
        order.setOwnerId(user.getId());
        order.setDeptId(user.getDeptId());
        order.setStatus(OrderStatus.UNCOMPLETED);

        int rtn;
        try {
            rtn = orderService.create(order);
        } catch (Exception e) {
            System.out.println("------------------------------------------");
            return Result.error(4001, "创建失败", HttpStatus.BAD_REQUEST);
        }
        if (rtn == 1) {
            return Result.ok(order);
        } else {
            return Result.error(4001, "创建失败", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * 更新order
     *
     * @return
     */
    @PutMapping("/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) Order order
    ) {
        order.setId(id);
        int rtn = orderService.update(order);
        logger.error("order" + order);
        if (rtn != 1) {
            return Result.error(4001, "更新失败");
        }
        return Result.ok(1);
    }

    /**
     * 将order转移
     *
     * @param morderId
     * @param toId
     * @return
     */
    @PostMapping("/{id}/to/{toId}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> transfer(
            @CurrentUser User user,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("toId") Long toId
    ) {
        int cnt = orderService.updateTransfer(morderId, user.getId(), toId);
        if (cnt != 1) {
            return Result.error(4001, "转移失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> delete(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("id") Long id
    ) {
        int rtn = orderService.delete(id);
        return Result.ok(1);
    }

}

