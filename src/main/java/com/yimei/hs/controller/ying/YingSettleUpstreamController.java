package com.yimei.hs.controller.ying;

import com.yimei.hs.entity.YingSettleUpstream;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/21.
 */
@Controller
@RestController("/api/ying")
public class YingSettleUpstreamController {

    /**
     * 获取所有huikuan
     *
     * @return
     */
    @GetMapping("/{orderId}/settleupstream")
    public ResponseEntity<PageResult<YingSettleUpstream>> list() {
        return PageResult.ok(null);
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/settleupstream/:id")
    public ResponseEntity<Result<YingSettleUpstream>> read(
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
    @PostMapping("/{orderId}/settleupstream")
    public ResponseEntity<Result<YingSettleUpstream>> create(@PathVariable("orderId") long orderId) {
        return Result.ok(null);
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @PutMapping("/{orderId}/settleupstream/:id")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        return Result.ok(1);
    }
}
