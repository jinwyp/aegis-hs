package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.ying.entity.YingHuikuan;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.ying.dto.PageYingHuikuanDTO;
import com.yimei.hs.ying.service.YingHuikuanService;
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
public class YingHuikuanController {

    private static final Logger logger = LoggerFactory.getLogger(YingHuikuanController.class);

    @Autowired
    private YingHuikuanService yingHuikuanService;

    /**
     * 获取所有huikuan
     *
     * @return
     */
    @GetMapping("/{orderId}/huikuans")
    public ResponseEntity<PageResult<YingHuikuan>> list(PageYingHuikuanDTO pageYingHuikuanDTO) {
        return PageResult.ok(yingHuikuanService.getPage(pageYingHuikuanDTO));
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/huikuans/{id}")
    public ResponseEntity<Result<YingHuikuan>> read(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        YingHuikuan huikuan = yingHuikuanService.findOne(id);
        if (huikuan == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(huikuan);
        }
    }

    /**
     * 创建huikuan
     *
     * @return
     */
    @PostMapping("/{orderId}/huikuans")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<YingHuikuan>> create(
            @PathVariable("orderId") long orderId,
            @RequestBody YingHuikuan  yingHuikuan
    ) {
        yingHuikuan.setOrderId(orderId);
        int cnt = yingHuikuanService.create(yingHuikuan);
        return Result.ok(null);
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @PutMapping("/{orderId}/huikuans/:id")
    @Transactional(readOnly =  false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) YingHuikuan yingHuikuan
    ) {
        assert (yingHuikuan.getOrderId() == orderId);
        yingHuikuan.setId(id);
        int cnt = yingHuikuanService.update(yingHuikuan);

        // 删除以前的对应记录
        // 重新插入对应记录  todo

        if (cnt != 1) {
            return Result.error(4001, "更新失败");
        }
        return Result.ok(1);
    }
}
