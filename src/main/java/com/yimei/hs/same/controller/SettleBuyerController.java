package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageSettleBuyerDTO;
import com.yimei.hs.same.entity.SettleBuyer;
import com.yimei.hs.same.service.SettleBuyerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * Created by hary on 2017/9/21.
 */
@RestController
@RequestMapping("/api/business/{businessType}")
@Logined
public class SettleBuyerController {

    private static final Logger logger = LoggerFactory.getLogger(SettleBuyerController.class);


    @Autowired
    private SettleBuyerService settleBuyeService;

    /**
     * 获取所有下游结算
     *
     * @return
     */
    @GetMapping("/{morderId}/settledownstream")
    public ResponseEntity<Result<Page<SettleBuyer>>> list(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            PageSettleBuyerDTO pageSettleBuyerDTO
    ) {
        pageSettleBuyerDTO.setOrderId(morderId);
        return Result.ok(settleBuyeService.getPage(pageSettleBuyerDTO));
    }

    /**
     * 获取下游结算
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/settledownstream/{id}")
    public ResponseEntity<Result<SettleBuyer>> read(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {

        SettleBuyer settleDownstream = settleBuyeService.findOne(id);
        if (settleDownstream == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(settleDownstream);
        }

    }

    /**
     * 创建下游结算
     *
     * @return
     */
    @PostMapping("/{morderId}/settledownstream")
    public ResponseEntity<Result<SettleBuyer>> create(
            @PathVariable("businessType") BusinessType businessType,
            @RequestBody @Validated(CreateGroup.class) SettleBuyer settleBuyer
    ) {
        settleBuyeService.create(settleBuyer);
        return Result.ok(settleBuyer);
    }

    /**
     * 更新下游结算
     *
     * @return
     */
    @PutMapping("/{morderId}/settledownstream/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) SettleBuyer settleBuyer
    ) {
        assert (settleBuyer.getOrderId() == morderId);
        int rtn = settleBuyeService.update(settleBuyer);
        if (rtn != 1) {
            logger.error("更新失败: {}", settleBuyer);
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }

        return Result.ok(1);
    }

    /**
     * 删除下游结算
     *
     * @return
     */
    @DeleteMapping("/{morderId}/settledownstream/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int rtn = settleBuyeService.delete(id);
        if (rtn != 1) {
            return Result.error(4001, "删除失败", HttpStatus.NOT_FOUND);
        }

        return Result.ok(1);
    }

}
