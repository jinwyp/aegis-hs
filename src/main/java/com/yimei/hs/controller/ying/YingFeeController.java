package com.yimei.hs.controller.ying;

import com.yimei.hs.entity.YingFee;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import com.yimei.hs.entity.dto.ying.PageYingFeeDTO;
import com.yimei.hs.service.ying.YingFeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        return PageResult.ok(yingFeeService.getPage(pageYingFeeDTO));
    }

    /**
     * 获取fee
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/fees/{id}")
    public ResponseEntity<Result<YingFee>> read(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        return Result.ok(yingFeeService.findOne(id));
    }

    /**
     * 创建fee
     *
     * @return
     */
    @PostMapping("/{orderId}/fees")
    public ResponseEntity<Result<YingFee>> create(@RequestBody YingFee yingFee) {
        yingFeeService.create(yingFee);
        return Result.ok(yingFee);
    }

    /**
     * 更新fee
     *
     * @return
     */
    @PutMapping("/{orderId}/fees/{id}")
    public ResponseEntity<Result<Integer>> update(YingFee yingFee) {
        yingFeeService.update(yingFee);
        return Result.ok(1);
    }
}
