package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.dto.PageYingOrderPartyDTO;
import com.yimei.hs.ying.entity.YingOrderParty;
import com.yimei.hs.ying.service.YingPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/25.
 */

@RequestMapping("/api/ying")
@RestController
@Logined
public class OrderPartyController {

    @Autowired
    YingPartyService yingPartyService;


    /**
     * 获取分页数据party - 一个订单(业务线)所有参与方客户
     *
     * @return
     */
    @GetMapping("/{morderId}/parties")
    public ResponseEntity<Result<Page<YingOrderParty>>> list(
            @PathVariable("morderId") Long morderId,
            PageYingOrderPartyDTO pageYingOrderPartyDTO
    ) {
        pageYingOrderPartyDTO.setOrderId(morderId);
        return Result.ok(yingPartyService.getPage(pageYingOrderPartyDTO));
    }


    /**
     * update
     */
    @PutMapping("/{morderId}/parties/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id,
            @RequestBody @Validated(UpdateGroup.class) YingOrderParty yingOrderParty
    ) {

        yingOrderParty.setOrderId(morderId);
        yingOrderParty.setId(id);
        int status = yingPartyService.update(yingOrderParty);
        if (status == 1) {
            return Result.ok(1);
        } else {
            return Result.error(5003, "操作失败", HttpStatus.NOT_FOUND);
        }

    }

    /**
     * 逻辑删除
     */
    @DeleteMapping("/{morderId}/parties/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id
    ) {

        int status = yingPartyService.delete(id);
        if (status == 1) {
            return Result.ok(1);
        } else {
            return Result.error(5003, "操作失败", HttpStatus.NOT_FOUND);
        }

    }

}
