package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageJiekuanDTO;
import com.yimei.hs.same.entity.Jiekuan;
import com.yimei.hs.same.service.JiekuanService;
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
public class JiekuanController {

    private static final Logger logger = LoggerFactory.getLogger(JiekuanController.class);


    @Autowired
    private JiekuanService jiekuanService;


    /**
     * 获取所有huankuan
     *
     * @return
     */
    @GetMapping("/{morderId}/jiekuans")
    public ResponseEntity<Result<Page<Jiekuan>>> list(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            PageJiekuanDTO pageJiekuanDTO) {
        pageJiekuanDTO.setOrderId(morderId);
        Page<Jiekuan> page = jiekuanService.getPage(pageJiekuanDTO);
        return Result.ok(page);
    }

    /**
     * 获取huankuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/jiekuans/{id}")
    public ResponseEntity<Result<Jiekuan>> read(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        Jiekuan fukuan = jiekuanService.findOne(id);
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
    @PostMapping("/{morderId}/jiekuans")
    public ResponseEntity<Result<Jiekuan>> create(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @RequestBody @Validated(CreateGroup.class) Jiekuan yingJiekuan
    ) {
        yingJiekuan.setOrderId(morderId);
        int rtn = jiekuanService.create(yingJiekuan);
        if (rtn != 1) {
            logger.error("创建失败: {}", yingJiekuan);
            return Result.error(4001, "创建失败");
        }
        return Result.ok(yingJiekuan);
    }

    /**
     * 更新huankuan
     *
     * @return
     */
    @PutMapping("/{morderId}/jiekuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) Jiekuan yingJiekuan
    ) {
        assert (yingJiekuan.getOrderId() == morderId);
        yingJiekuan.setId(id);
        int rtn = jiekuanService.update(yingJiekuan);
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
    @DeleteMapping("/{morderId}/jiekuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int rtn = jiekuanService.delete(morderId, id);
        if (rtn != 1) {
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }


}
