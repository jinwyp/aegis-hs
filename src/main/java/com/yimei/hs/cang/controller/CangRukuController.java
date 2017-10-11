package com.yimei.hs.cang.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.cang.dto.PageCangRukuDTO;
import com.yimei.hs.cang.entity.CangRuku;
import com.yimei.hs.cang.service.CangRukuService;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageFeeDTO;
import com.yimei.hs.same.entity.Fee;
import com.yimei.hs.same.service.FeeService;
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
public class CangRukuController {


    private static final Logger logger = LoggerFactory.getLogger(CangRukuController.class);

    @Autowired
    CangRukuService cangRukuService;


    /**
     * 获取分页数据cangRuku
     *
     * @return
     */
    @GetMapping("/{morderId}/rukus")
    public ResponseEntity<Result<Page<CangRuku>>> list(
            @PathVariable("morderId") Long morderId,
            PageCangRukuDTO pageCangRu) {
        pageCangRu.setOrderId(morderId);
        Page<CangRuku> page = cangRukuService.getPage(pageCangRu);
        return Result.ok(page);
    }

    /**
     * @param morderId
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/rukus/{id}")
    public ResponseEntity<Result<CangRuku>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        CangRuku cangRuku = cangRukuService.findOne(id);
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
    @PostMapping("/{morderId}/rukus")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<CangRuku>> create(
            @RequestBody @Validated(CreateGroup.class) CangRuku cangRuku
    ) {
        int rtn = cangRukuService.create(cangRuku);
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
    @PutMapping("/{morderId}/rukus/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id,
            @RequestBody @Validated(UpdateGroup.class) CangRuku cangRuku
    ) {
        assert (morderId == cangRuku.getOrderId());
        cangRuku.setId(id);
        int rtn = cangRukuService.update(cangRuku);
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
    @DeleteMapping("/{morderId}/rukus/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> delete(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id
    ) {
        int rtn = cangRukuService.delete(id);
        if (rtn != 1) {
            return Result.error(5001, "删除失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }
}
