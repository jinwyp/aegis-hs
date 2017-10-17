package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageOrderConfigDTO;
import com.yimei.hs.same.entity.OrderConfig;
import com.yimei.hs.same.service.OrderConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/25.
 */
@RestController
@RequestMapping("/api/business/{businessType}")
public class OrderConfigController {


    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderConfigService orderConfigService;


    /**
     * 获取分页数据应收订单核月配置
     *
     * @return
     */
    @GetMapping("/{morderId}/units")
    public ResponseEntity<Result<Page<OrderConfig>>> list(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            PageOrderConfigDTO pageOrderConfigDTO
    ) {
        pageOrderConfigDTO.setOrderId(morderId);
        return Result.ok(orderConfigService.getPage(pageOrderConfigDTO));
    }

    /**
     * 创建核算月配置
     *
     * @param orderConfig
     * @return
     */
    @PostMapping("/{morderId}/units")
    public ResponseEntity<Result<OrderConfig>> create(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @RequestBody OrderConfig orderConfig) {

        orderConfig.setOrderId(morderId);
        int rtn = orderConfigService.create(orderConfig);
        if (rtn != 1) {
            logger.error("创建失败: {}", orderConfig);
            return Result.error(5001, "创建失败");
        } else {
            return Result.ok(orderConfig);
        }
    }

    /**
     * 查询核算月数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/units/{id}")
    public ResponseEntity<Result<OrderConfig>> findOne(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id
    ) {
        OrderConfig orderConfig = orderConfigService.findOne(id);

        if (orderConfig == null) {
            return Result.error(4001, "记录不存在");
        } else {
            return Result.ok(orderConfig);
        }
    }

    /**
     *  更新核算月
     * @param businessType
     * @param morderId
     * @param id
     * @param orderConfig
     * @return
     */

    @PutMapping("/{morderId}/units/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id,
            @RequestBody @Validated(UpdateGroup.class) OrderConfig orderConfig) {


      OrderConfig orderConfigDB=  orderConfigService.findOne(id);
        if (orderConfigDB.getOrderId() == morderId) {
            logger.debug("orderConfig-====>" + id);

            orderConfig.setId(id);
            orderConfig.setOrderId(morderId);

            logger.debug("orderConfig-====>" + orderConfig);
            int status = orderConfigService.update(orderConfig);
            if (status == 1) {
                return Result.ok(1);
            } else {
                return Result.error(5003, "更新失败");
            }
        } else {
            return Result.error(5003, "非法操作");
        }

    }

}

