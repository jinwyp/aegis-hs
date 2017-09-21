package com.yimei.hs.controller.ying;

import com.yimei.hs.entity.YingSettleTraffic;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/21.
 */

/**
 * Created by hary on 2017/9/21.
 */
@RestController("/api/ying")
public class YingSettleTrafficController {

    /**
     * 获取所有huikuan
     *
     * @return
     */
    @GetMapping("/{orderId}/settletraffic")
    public ResponseEntity<PageResult<YingSettleTraffic>> list() {
        return PageResult.ok(null);
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/settletraffic/:id")
    public ResponseEntity<Result<YingSettleTraffic>> read(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        return Result.ok(null);
    }

    /**
     * 创建huikuan
     *
     * @return
     */
    @PostMapping("/{orderId}/settletraffic")
    public ResponseEntity<Result<YingSettleTraffic>> create(@PathVariable("orderId") long orderId) {
        return Result.ok(null);
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @PutMapping("/{orderId}/settletraffic/:id")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        return Result.ok(1);
    }
}
