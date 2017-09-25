package com.yimei.hs.ying.controller;

import com.yimei.hs.ying.entity.YingFukuan;
import com.yimei.hs.boot.PageResult;
import com.yimei.hs.boot.Result;
import com.yimei.hs.ying.dto.PageYingFukuanDTO;
import com.yimei.hs.ying.service.YingFukuanService;
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
        YingFukuan fukuan = yingFukuanService.findOne(id);
        if (fukuan == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(fukuan);
        }
    }

    /**
     * 创建huankuan
     *
     * @return
     */
    @PostMapping("/{orderId}/fukuans")
    public ResponseEntity<Result<YingFukuan>> create(YingFukuan yingFukuan) {
        yingFukuanService.create(yingFukuan);
        return Result.ok(yingFukuan);
    }

    /**
     * 更新huankuan
     *
     * @return
     */
    @PutMapping("/{orderId}/fukuans/:id")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id,
            @RequestBody YingFukuan yingFukuan
    ) {
        assert (yingFukuan.getOrderId() == orderId);
        yingFukuan.setId(id);
        int rtn = yingFukuanService.update(yingFukuan);
        if (rtn != 1) {
            return Result.error(4001, "更新失败");
        }
        return Result.ok(1);
    }
}
