package com.yimei.hs.cang.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.cang.entity.CangRuku;
import com.yimei.hs.cang.service.CangChukuService;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.cang.dto.PageCangChukuDTO;
import com.yimei.hs.cang.entity.CangChuku;
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
@RestController
@RequestMapping("/api/cang")
public class CangChukuController {

    private static final Logger logger = LoggerFactory.getLogger(CangChukuController.class);

    @Autowired
    CangChukuService cangChukuService;


    @GetMapping("/{morderId}/chukus")
    public ResponseEntity<Result<Page<CangChuku>>> list(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            PageCangChukuDTO pageCangChukuDTO
    ) {
        // pageChukuDTO.setOrderId(morderId);
        Page<CangChuku> page = cangChukuService.getPage(pageCangChukuDTO);
        return Result.ok(page);
    }

    /**
     * @param morderId
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/chukus/{id}")
    public ResponseEntity<Result<CangChuku>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        CangChuku cangRuku = cangChukuService.findOne(id);
        if (cangRuku == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(cangRuku);
        }
    }

    /**
     * 创建cangRuku
     *
     * @return
     */
    @PostMapping("/{morderId}/chukus")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<CangChuku>> create(
            @RequestBody @Validated(CreateGroup.class) CangChuku cangRuku
    ) {
        int rtn = cangChukuService.create(cangRuku);
        if (rtn != 1) {
            logger.error("创建失败: {}", cangRuku);
            return Result.error(5001, "创建失败", HttpStatus.NOT_ACCEPTABLE);
        }
        return Result.ok(cangRuku);
    }

    /**
     * 更新cangRuku
     *
     * @return
     */
    @PutMapping("/{morderId}/chukus/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id,
            @RequestBody @Validated(UpdateGroup.class) CangChuku cangRuku
    ) {
        assert (morderId == cangRuku.getOrderId());
        cangRuku.setId(id);
        int rtn = cangChukuService.update(cangRuku);
        if (rtn != 1) {
            return Result.error(5001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }

    /**
     * 删除cangRuku
     *
     * @return
     */
    @DeleteMapping("/{morderId}/chukus/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> delete(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id
    ) {
        int rtn = cangChukuService.delete(id);
        if (rtn != 1) {
            return Result.error(5001, "删除失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }

}
