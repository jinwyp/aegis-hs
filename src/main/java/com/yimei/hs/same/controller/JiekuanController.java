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

import java.util.List;

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
     * 获取所有借款
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
     * 获取所有借款
     *
     * @return
     */
    @GetMapping("/{morderId}/jiekuansUnfinished")
    public ResponseEntity<Result<List<Jiekuan>>> getListUnfinished(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId) {
        return Result.ok(jiekuanService.getListUnfinished(morderId));
    }

    /**
     * 获取借款
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/jiekuans/{id}")
    public ResponseEntity<Result<Jiekuan>> read(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id
    ) {
        Jiekuan fukuan = jiekuanService.findOne(id);
        if (fukuan == null) {
            return Result.error(4001, "记录不存在");
        } else {
            return Result.ok(fukuan);
        }
    }

    /**
     * 创建借款
     *
     * @return
     */
    @PostMapping("/{morderId}/jiekuans")
    public ResponseEntity<Result<Jiekuan>> create(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @RequestBody @Validated(CreateGroup.class) Jiekuan jiekuan
    ) {
        jiekuan.setOrderId(morderId);
        int rtn = jiekuanService.create(jiekuan);
        if (rtn != 1) {
            logger.error("创建失败: {}", jiekuan);
            return Result.error(4001, "创建失败");
        }
        return Result.ok(jiekuan);
    }

    /**
     * 更新借款
     *
     * @return
     */
    @PutMapping("/{morderId}/jiekuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) Jiekuan jiekuan
    ) {
        assert (jiekuan.getOrderId() == morderId);
        jiekuan.setId(id);
        int rtn = jiekuanService.update(jiekuan);
        if (rtn != 1) {
            return Result.error(4001, "更新失败");
        }
        return Result.ok(1);
    }

    /**
     * 删除借款
     *
     * @return
     */
    @DeleteMapping("/{morderId}/jiekuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int rtn = jiekuanService.delete(id);
        if (rtn != 1) {
            return Result.error(4001, "更新失败");
        }
        return Result.ok(1);
    }


}
