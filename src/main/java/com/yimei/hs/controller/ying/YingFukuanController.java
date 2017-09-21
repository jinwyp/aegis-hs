package com.yimei.hs.controller.ying;

import com.yimei.hs.entity.YingFukuan;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/ying")
@RestController
public class YingFukuanController {

    /**
     * 获取所有huankuan
     *
     * @return
     */
    @GetMapping("/{orderId}/huankuans")
    public ResponseEntity<PageResult<YingFukuan>> list(@PathVariable("orderId") long orderId) {
        return PageResult.ok(null);
    }

    /**
     * 获取huankuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/huankuans/:id")
    public ResponseEntity<Result<YingFukuan>> read(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        return Result.ok(null);
    }

    /**
     * 创建huankuan
     *
     * @return
     */
    @PostMapping("/{orderId}/huankuans")
    public ResponseEntity<Result<YingFukuan>> create(@PathVariable("orderId") long orderId) {
        return Result.ok(null);
    }

    /**
     * 更新huankuan
     *
     * @return
     */
    @PutMapping("/{orderId}/huankuan/:id")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        return Result.ok(1);
    }
}
