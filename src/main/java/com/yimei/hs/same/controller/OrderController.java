package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.CurrentUser;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.OrderStatus;
import com.yimei.hs.same.dto.PageFukuanDTO;
import com.yimei.hs.same.dto.PageOrderDTO;
import com.yimei.hs.same.entity.Fukuan;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.entity.OrderConfig;
import com.yimei.hs.same.service.FukuanService;
import com.yimei.hs.same.service.OrderConfigService;
import com.yimei.hs.same.service.OrderService;
import com.yimei.hs.same.service.SettleSellerService;
import com.yimei.hs.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private FukuanService fukuanService;
    @Autowired
    private OrderConfigService orderConfigService;

    @Autowired
    private SettleSellerService settleSellerService;

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
        logger.debug("获取订单分页: {}, {}", user, pageOrderDTO);
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
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<Result<Order>> create(
            @CurrentUser User user,
            @PathVariable("businessType") BusinessType businessType,
            @RequestBody @Validated(CreateGroup.class) Order order) {

        assert (businessType.equals(order.getBusinessType()));

        order.setCreatorId(user.getId());
        order.setBusinessType(businessType);
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
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) Order order
    ) {

        PageFukuanDTO pageFukuanDTO = new PageFukuanDTO();
        pageFukuanDTO.setOrderId(order.getId());

        List<Fukuan> fukuans=fukuanService.getPage(pageFukuanDTO).getResults();
        if (fukuans == null || fukuans.size() == 0) {
            order.setId(id);
            order.setBusinessType(businessType);
            int rtn = orderService.update(order,OrderStatus.UNCOMPLETED);

            if (rtn != 1) {
                return Result.error(4001, "更新失败");
            }
            return Result.ok(1);
        } else {
            return Result.error(4001, "订单已经生效，不能修改");
        }


    }


    /**
     * 更新order
     *
     * @return
     */
    @PutMapping("/status/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("id") long id
    ) {


        boolean setAble = true;
        List<OrderConfig> orderConfigs = orderConfigService.getList(id);

        for (OrderConfig orderConfig :orderConfigs) {
            setAble = settleSellerService.selectHsAndOrderId(id, orderConfig.getId());
            if (setAble==false) {
                break;
            }
        }
        if (setAble) {

            Order order = new Order();
            order.setId(id);
            order.setBusinessType(businessType);
            order.setStatus(OrderStatus.COMPLETED);

            int rtn = orderService.update(order,OrderStatus.COMPLETED);

            if (rtn != 1) {
                return Result.error(4001, "更新失败");
            }
            return Result.ok(1);
        } else {
            return Result.error(4001, "订单已经生效，不能修改");
        }


    }
    /**
     * 将order转移
     *
     * @param morderId
     * @param toId
     * @return
     */
    @PostMapping("/{morderId}/to/{toId}")
    public ResponseEntity<Result<Integer>> transfer(
            @CurrentUser User user,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("toId") Long toId
    ) {
        logger.debug("订单转移: orderId = {}, businessType = {} to = {} user = {}", morderId, businessType, toId, user);
        int cnt = orderService.updateTransfer(morderId, user.getId(), toId);
        if (cnt != 1) {
            return Result.error(4001, "转移失败", HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<Result<Integer>> delete(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("id") Long id
    ) {
        int rtn = orderService.delete(id);
        return Result.ok(1);
    }

}

