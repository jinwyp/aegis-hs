package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingFukuan;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.ying.dto.PageYingFukuanDTO;
import com.yimei.hs.ying.entity.YingHuankuan;
import com.yimei.hs.ying.entity.YingHuankuanMap;
import com.yimei.hs.ying.entity.YingHuikuanMap;
import com.yimei.hs.ying.service.YingFukuanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

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
    public ResponseEntity<Result<Page<YingFukuan>>> list(
            @PathVariable("morderId") Long morderId,
            PageYingFukuanDTO pageYingFukuanDTO) {

        //
        if (pageYingFukuanDTO.getHuankuanUnfinished() != null
                && pageYingFukuanDTO.getHuankuanUnfinished()
                && pageYingFukuanDTO.getHuikuanUnfinished() != null
                && pageYingFukuanDTO.getHuikuanUnfinished()
                ) {
            return Result.error(4001, "参数非法");
        }

        pageYingFukuanDTO.setOrderId(morderId);
        Page<YingFukuan> page = yingFukuanService.getPage(pageYingFukuanDTO);

        return Result.ok(page);
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
    @Transactional(readOnly = false)
    public ResponseEntity<Result<YingFukuan>> create(
            @PathVariable("morderId") Long morderId,
            @RequestBody @Validated(CreateGroup.class) YingFukuan yingFukuan
    ) {
        yingFukuan.setOrderId(morderId);
        int rtn = yingFukuanService.create(yingFukuan);
        if (rtn != 1) {
            logger.error("创建失败: {}", yingFukuan);
            return Result.error(4001, "创建失败");
        }
        return Result.ok(yingFukuan);
    }

    /**
     * 更新huankuan
     *
     * @return
     */
    @Transactional(readOnly = false)
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

    /**
     * 更新付款
     *
     * @return
     */
    @Transactional(readOnly = false)
    @DeleteMapping("/{morderId}/fukuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int rtn = yingFukuanService.delete(morderId, id);
        if (rtn != 1) {
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }


}
