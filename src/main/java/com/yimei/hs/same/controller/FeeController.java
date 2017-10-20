package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageFeeDTO;
import com.yimei.hs.same.entity.Fee;
import com.yimei.hs.same.service.FeeService;
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
@RequestMapping("/api/business/{businessType}")
@RestController
@Logined
public class FeeController {


    private static final Logger logger = LoggerFactory.getLogger(FeeController.class);

    @Autowired
    FeeService feeService;


    /**
     * 获取分页数据fee
     *
     * @return
     */
    @GetMapping("/{morderId}/fees")
    public ResponseEntity<Result<Page<Fee>>> list(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            PageFeeDTO pageFeeDTO) {
        pageFeeDTO.setOrderId(morderId);
        Page<Fee> page = feeService.getPage(pageFeeDTO);
        return Result.ok(page);
    }

    /**
     * @param morderId
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/fees/{id}")
    public ResponseEntity<Result<Fee>> read(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        Fee fee = feeService.findOne(id);
        if (fee == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(fee);
        }
    }

    /**
     * 创建fee
     *
     * @return
     */
    @PostMapping("/{morderId}/fees")
    public ResponseEntity<Result<Fee>> create(
            @PathVariable("businessType") BusinessType businessType,
            @RequestBody @Validated(CreateGroup.class) Fee fee
    ) {
        int rtn = feeService.create(fee);
        if (rtn != 1) {
            logger.error("创建失败: {}", fee);
            return Result.error(5001, "创建失败", HttpStatus.NOT_ACCEPTABLE);
        }
        return Result.ok(fee);
    }

    /**
     * 更新fee
     *
     * @return
     */
    @PutMapping("/{morderId}/fees/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id,
            @RequestBody @Validated(UpdateGroup.class) Fee fee
    ) {
        assert (morderId == fee.getOrderId());
        fee.setId(id);
        int rtn = feeService.update(fee);
        if (rtn != 1) {
            return Result.error(5001, "更新失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }

    /**
     * 删除fee
     *
     * @return
     */
    @DeleteMapping("/{morderId}/fees/{id}")
    public ResponseEntity<Result<Integer>> delete(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id
    ) {
        int rtn = feeService.delete(id);
        if (rtn != 1) {
            return Result.error(5001, "删除失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }
}
