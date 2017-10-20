package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.CustomerType;
import com.yimei.hs.same.dto.PageOrderPartyDTO;
import com.yimei.hs.same.entity.OrderParty;
import com.yimei.hs.same.service.OrderPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hary on 2017/9/25.
 */

@RequestMapping("/api/business/{businessType}")
@RestController
@Logined
public class OrderPartyController {

    @Autowired
    OrderPartyService orderPartyService;


    /**
     * 获取分页数据party - 一个订单(业务线)所有参与方客户
     *
     * @return
     */
    @GetMapping("/{morderId}/parties")
    public ResponseEntity<Result<Page<OrderParty>>> list(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            PageOrderPartyDTO pageOrderPartyDTO
    ) {
        pageOrderPartyDTO.setOrderId(morderId);
        return Result.ok(orderPartyService.getPage(pageOrderPartyDTO));
    }

    @GetMapping("/{morderId}/parties{customerType}")
    public ResponseEntity<Result<List<OrderParty>>>getOrderPartyLisTByType(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("customerType")CustomerType customerType
            ){
        return Result.ok(orderPartyService.getOrderPartyListByType(morderId,customerType));

    }
    /**
     * update
     */
    @PutMapping("/{morderId}/parties/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id,
            @RequestBody @Validated(UpdateGroup.class) OrderParty orderParty
    ) {

        orderParty.setOrderId(morderId);
        orderParty.setId(id);
        int status = orderPartyService.update(orderParty);
        if (status == 1) {
            return Result.ok(1);
        } else {
            return Result.error(5003, "操作失败", HttpStatus.BAD_REQUEST);
        }

    }




    /**
     * 逻辑删除
     */
    @DeleteMapping("/{morderId}/parties/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id
    ) {

        int status = orderPartyService.delete(id);
        if (status == 1) {
            return Result.ok(1);
        } else {
            return Result.error(5003, "操作失败", HttpStatus.BAD_REQUEST);
        }

    }


}
