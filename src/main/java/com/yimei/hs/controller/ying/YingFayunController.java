package com.yimei.hs.controller.ying;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingFayun;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/ying")
@RestController
public class YingFayunController {

    private static final Logger logger = LoggerFactory.getLogger(YingFayunController.class);


    /**
     * 获取所有fayun
     *
     * @return
     */
    @GetMapping("/{orderId}/fayuns")
    public ResponseEntity<PageResult<YingFayun>> list(
            @PathVariable("orderId") Long orderId,
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize) {
        Page<YingFayun> page = null;
        return PageResult.ok(page);
    }

    /**
     * 获取fayun
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}fayuns/:id")
    public ResponseEntity<Result<YingFayun>> read(
            @PathVariable("orderId") Long orderId,
            @PathVariable("id") long id) {
        YingFayun fayun = null;
        return Result.ok(fayun);
    }

    /**
     * 创建fayun
     *
     * @return
     */
    @PostMapping("/{orderId}/fayuns")
    public ResponseEntity<Result<YingFayun>> create(@PathVariable("orderId") long orderId) {
        YingFayun fayun = null;
        return Result.ok(fayun);
    }

    /**
     * 更新fayun
     *
     * @return
     */
    @PutMapping("/{orderId}/fayuns/:id")
    public ResponseEntity<Result<YingFayun>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        YingFayun fayun = null;
        return Result.ok(fayun);
    }


    // 发运统计
    @Data
    private static class FayunStat {
    }

    /**
     *  发运统计
     */
    @GetMapping("/{orderId}/fayuns-stat")
    public ResponseEntity<Result<FayunStat>> stat(
            @PathVariable("orderId") long orderId
    ) {
        return Result.ok(null);
    }

}
