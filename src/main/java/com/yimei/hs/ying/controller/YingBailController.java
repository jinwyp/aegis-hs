package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.TrafficMode;
import com.yimei.hs.ying.dto.PageYingBailDTO;
import com.yimei.hs.ying.entity.YingBail;
import com.yimei.hs.ying.entity.YingBail;
import com.yimei.hs.ying.service.YingBailService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
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
public class YingBailController {

    private static final Logger logger = LoggerFactory.getLogger(YingBailController.class);

    @Autowired
    YingBailService yingBailService;

    /**
     * 获取所有bail
     *
     * @return
     */
    @GetMapping("/{morderId}/bails")
    public ResponseEntity<Result<Page<YingBail>>> list(
            @PathVariable("morderId") Long morderId,
            @PathVariable("businessType") BusinessType businessType,
            PageYingBailDTO pageYingBailDTO) {
        pageYingBailDTO.setOrderId(morderId);
        Page<YingBail> page = yingBailService.getPage(pageYingBailDTO);
        return Result.ok(page);
    }

    /**
     * 获取bail
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/bails/{id}")
    public ResponseEntity<Result<YingBail>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("id") long id) {

        YingBail bail = yingBailService.findOne(id);
        if (bail == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(bail);
        }
    }

    /**
     * 创建bail
     *
     * @return
     */
    @PostMapping("/{morderId}/bails")
    public ResponseEntity<Result<YingBail>> create(
            @PathVariable("morderId") Long morderId,
            @PathVariable("businessType") BusinessType businessType,
            @RequestBody @Validated(CreateGroup.class) YingBail yingBail
    ) {
        int rtn = yingBailService.create(yingBail);
        if (rtn != 1) {
            return Result.error(4001, "创建失败");
        } else {
            return Result.ok(yingBail);
        }
    }

    /**
     * 更新bail
     *
     * @return
     */
    @PutMapping("/{morderId}/bails/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id,
            @PathVariable("businessType") BusinessType businessType,
            @RequestBody @Validated(UpdateGroup.class) YingBail yingBail) {
        yingBail.setId(id);
        int cnt = yingBailService.update(yingBail);
        if (cnt != 1) {
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }

    /**
     * 逻辑删除
     *
     * @param morderId
     * @param id
     * @return
     */
    @DeleteMapping("/{morderId}/bails/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("id") Long id
    ) {
        int cnt = yingBailService.delete(morderId, id);
        if (cnt != 1) {
            return Result.error(4001, "删除失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }

}
