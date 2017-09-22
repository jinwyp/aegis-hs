package com.yimei.hs.controller.ying;

import com.yimei.hs.entity.YingFukuan;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import com.yimei.hs.entity.dto.ying.PageYingFukuanDTO;
import com.yimei.hs.service.ying.YingFukuanService;
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
public class YingFukuanController {

    private static final Logger logger = LoggerFactory.getLogger(YingFukuanController.class);


    @Autowired
    private YingFukuanService yingFukuanService;


    /**
     * 获取所有huankuan
     *
     * @return
     */
    @GetMapping("/{orderId}/fukuans")
    public ResponseEntity<PageResult<YingFukuan>> list(PageYingFukuanDTO pageYingFukuanDTO) {
        return PageResult.ok(yingFukuanService.getPage(pageYingFukuanDTO));
    }

    /**
     * 获取huankuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/fukuans/:id")
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
    @PostMapping("/{orderId}/fukuans")
    public ResponseEntity<Result<YingFukuan>> create(@PathVariable("orderId") long orderId) {
        return Result.ok(null);
    }

    /**
     * 更新huankuan
     *
     * @return
     */
    @PutMapping("/{orderId}/fukuans/:id")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        return Result.ok(1);
    }
}
