package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageFukuanDTO;
import com.yimei.hs.same.entity.Fukuan;
import com.yimei.hs.same.service.FukuanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/business/{businessType}")
@RestController
@Logined
public class FukuanController {

    private static final Logger logger = LoggerFactory.getLogger(FukuanController.class);


    @Autowired
    private FukuanService fukuanService;


    /**
     * 获取所有huankuan
     *
     * @return
     */
    @GetMapping("/{morderId}/fukuans")
    public ResponseEntity<Result<Page<Fukuan>>> list(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            PageFukuanDTO pageFukuanDTO) {

        //
        if ( pageFukuanDTO.getHuikuanUnfinished() != null
              && pageFukuanDTO.getHuikuanUnfinished()
                ) {
            return Result.error(4001, "参数非法");
        }

        pageFukuanDTO.setOrderId(morderId);
        Page<Fukuan> page = fukuanService.getPage(pageFukuanDTO);

        return Result.ok(page);
    }

    /**
     * 获取huankuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/fukuans/{id}")
    public ResponseEntity<Result<Fukuan>> read(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        Fukuan fukuan = fukuanService.findOne(id);
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
    public ResponseEntity<Result<Fukuan>> create(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @RequestBody @Validated(CreateGroup.class) Fukuan yingFukuan
    ) {
        yingFukuan.setOrderId(morderId);
        int rtn = fukuanService.create(yingFukuan);
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
    @PutMapping("/{morderId}/fukuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) Fukuan yingFukuan
    ) {
        assert (yingFukuan.getOrderId() == morderId);
        yingFukuan.setId(id);
        int rtn = fukuanService.update(yingFukuan);
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
    @DeleteMapping("/{morderId}/fukuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int rtn = fukuanService.delete(morderId, id);
        if (rtn != 1) {
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }


}
