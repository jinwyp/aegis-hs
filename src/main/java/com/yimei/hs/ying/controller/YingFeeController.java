package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingFee;
import com.yimei.hs.boot.PageResult;
import com.yimei.hs.boot.Result;
import com.yimei.hs.ying.dto.PageYingFeeDTO;
import com.yimei.hs.ying.service.YingFeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/ying")
@RestController
public class YingFeeController {


    private static final Logger logger = LoggerFactory.getLogger(YingFeeController.class);

    @Autowired
    YingFeeService yingFeeService;


    /**
     * 获取分页数据fee
     *
     * @return
     */
    @GetMapping("/{orderId}/fees")
    public ResponseEntity<PageResult<YingFee>> list(PageYingFeeDTO pageYingFeeDTO) {
        Page<YingFee> page = yingFeeService.getPage(pageYingFeeDTO);
        return PageResult.ok(page);
    }

    /**
     *
     * @param orderId
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/fees/{id}")
    public ResponseEntity<Result<YingFee>> read(
            @PathVariable("orderId") long orderId,
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
    @PostMapping("/{orderId}/fees")
    public ResponseEntity<Result<YingFee>> create(@RequestBody YingFee yingFee) {
        int rtn = yingFeeService.create(yingFee);
        if (rtn != 1) {
            return Result.error(5001, "创建失败");
        }
        return Result.ok(yingFee);
    }

    /**
     * 更新fee
     *
     * @return
     */
    @PutMapping("/{orderId}/fees/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") Long orderId,
            @PathVariable("id") Long id,
            YingFee yingFee) {
        assert (orderId == yingFee.getOrderId());
        yingFee.setId(id);
        int rtn = yingFeeService.update(yingFee);
        if (rtn != 1) {
            return Result.error(5001, "更新失败");
        }
        return Result.ok(1);
    }
}
