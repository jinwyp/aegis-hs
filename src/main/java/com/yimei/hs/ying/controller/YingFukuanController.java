package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.ying.entity.YingFukuan;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.ying.dto.PageYingFukuanDTO;
import com.yimei.hs.ying.service.YingFukuanService;
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
public class YingFukuanController {

    private static final Logger logger = LoggerFactory.getLogger(YingFukuanController.class);


    @Autowired
    private YingFukuanService yingFukuanService;


    /**
     * 获取所有huankuan
     *
     * @return
     */
    @GetMapping("/{morderId}/fukuans")
    public ResponseEntity<PageResult<YingFukuan>> list(
            @PathVariable("morderId") Long morderId,
            PageYingFukuanDTO pageYingFukuanDTO) {
        pageYingFukuanDTO.setOrderId(morderId);
        return PageResult.ok(yingFukuanService.getPage(pageYingFukuanDTO));
    }

    /**
     * 获取huankuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/fukuans/{id}")
    public ResponseEntity<Result<YingFukuan>> read(
            @PathVariable("morderId") Long morderId,
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
    @PostMapping("/{morderId}/fukuans")
    @Transactional(readOnly =  false)
    public ResponseEntity<Result<YingFukuan>> create(
            @PathVariable("morderId") Long morderId,
            @RequestBody @Validated(CreateGroup.class) YingFukuan yingFukuan
    ) {
        // todo 增加校验

        yingFukuan.setOrderId(morderId);
        yingFukuanService.create(yingFukuan);
        return Result.ok(yingFukuan);
    }

    /**
     * 更新huankuan
     *
     * @return
     */
    @Transactional(readOnly =  false)
    @PutMapping("/{morderId}/fukuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) YingFukuan yingFukuan
    ) {
        assert (yingFukuan.getOrderId() == morderId);
        yingFukuan.setId(id);
        int rtn = yingFukuanService.update(yingFukuan);
        if (rtn != 1) {
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }
}
