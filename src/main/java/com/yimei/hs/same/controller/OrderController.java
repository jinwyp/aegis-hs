package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.CurrentUser;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.OrderStatus;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.ying.dto.PageYingOrderDTO;
import com.yimei.hs.ying.entity.YingOrder;
import com.yimei.hs.ying.service.YingOrderService;
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
@RequestMapping("/api/yings")
@RestController
@Logined
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private YingOrderService yingOrderService;

    /**
     * 获取订单分页数据
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<Result<Page<YingOrder>>> list(
            @CurrentUser User user,
            PageYingOrderDTO pageYingOrderDTO
    ) {
        // 如果不是管理员， 就只展示自己的订单
        if (!user.getIsAdmin()) {
            pageYingOrderDTO.setOwnerId(user.getId());
        }

        return Result.ok(yingOrderService.getPage(pageYingOrderDTO));
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
    public ResponseEntity<Result<YingOrder>> create(
            @CurrentUser User user,
            @RequestBody @Validated(CreateGroup.class) YingOrder order) {
        order.setCreatorId(user.getId());
        order.setOwnerId(user.getId());
        order.setDeptId(user.getDeptId());
        order.setStatus(OrderStatus.UNCOMPLETED);

        int rtn;
        try {
            rtn = yingOrderService.create(order);
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
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) YingOrder yingOrder
    ) {
        yingOrder.setId(id);
        int rtn = yingOrderService.update(yingOrder);
        logger.error("yingOrder" + yingOrder);
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
            @PathVariable("morderId") Long morderId,
            @PathVariable("toId") Long toId
    ) {
        int cnt = yingOrderService.updateTransfer(morderId, user.getId(), toId);
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
            @PathVariable("id") Long id
    ) {
        int rtn = yingOrderService.delete(id);
        return Result.ok(1);
    }

}

