package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.ying.entity.YingFee;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.ying.dto.PageYingFeeDTO;
import com.yimei.hs.ying.service.YingFeeService;
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
@RequestMapping("/api/ying")
@RestController
@Logined
public class YingFeeController {


    private static final Logger logger = LoggerFactory.getLogger(YingFeeController.class);

    @Autowired
    YingFeeService yingFeeService;


    /**
     * 获取分页数据fee
     *
     * @return
     */
    @GetMapping("/{morderId}/fees")
    public ResponseEntity<PageResult<YingFee>> list(
            @PathVariable("morderId") Long morderId,
            PageYingFeeDTO pageYingFeeDTO) {
        pageYingFeeDTO.setOrderId(morderId);
        Page<YingFee> page = yingFeeService.getPage(pageYingFeeDTO);
        return PageResult.ok(page);
    }

    /**
     *
     * @param morderId
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/fees/{id}")
    public ResponseEntity<Result<YingFee>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        YingFee yingFee = yingFeeService.findOne(id);
        if (yingFee == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(yingFee);
        }
    }

    /**
     * 创建fee
     *
     * @return
     */
    @PostMapping("/{morderId}/fees")
    @Transactional(readOnly =  false)
    public ResponseEntity<Result<YingFee>> create(
            @RequestBody @Validated(CreateGroup.class) YingFee yingFee
    ) {
        int rtn = yingFeeService.create(yingFee);
        if (rtn != 1) {
            return Result.error(5001, "创建失败", HttpStatus.NOT_ACCEPTABLE);
        }
        return Result.created(yingFee);
    }

    /**
     * 更新fee
     *
     * @return
     */
    @PutMapping("/{morderId}/fees/{id}")
    @Transactional(readOnly =  false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id,
            @RequestBody @Validated(UpdateGroup.class) YingFee yingFee
    ) {
        assert (morderId == yingFee.getOrderId());
        yingFee.setId(id);
        int rtn = yingFeeService.update(yingFee);
        if (rtn != 1) {
            return Result.error(5001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }
}
