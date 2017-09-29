package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.ying.dto.PageYingSettleDownstreamDTO;
import com.yimei.hs.ying.entity.YingSettleDownstream;
import com.yimei.hs.ying.service.YingSettleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * Created by hary on 2017/9/21.
 */
@RestController
@RequestMapping("/api/ying")
@Logined
public class YingSettleDownstreamController {

    private static final Logger logger = LoggerFactory.getLogger(YingSettleDownstreamController.class);


    @Autowired
    private YingSettleService yingSettleService;

    /**
     * 获取所有下游结算
     *
     * @return
     */
    @GetMapping("/{morderId}/settledownstream")
    public ResponseEntity<PageResult<YingSettleDownstream>> list(
            @PathVariable("morderId") Long morderId,
            PageYingSettleDownstreamDTO pageYingSettleDownstreamDTO
    ) {
        pageYingSettleDownstreamDTO.setOrderId(morderId);
        return PageResult.ok(yingSettleService.getPageDownstream(pageYingSettleDownstreamDTO));
    }

    /**
     * 获取下游结算
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/settledownstream/{id}")
    public ResponseEntity<Result<YingSettleDownstream>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {

        YingSettleDownstream settleDownstream = yingSettleService.findDownstream(id);
        if (settleDownstream == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(settleDownstream);
        }

    }

    /**
     * 创建下游结算
     *
     * @return
     */
    @Transactional(readOnly = false)
    @PostMapping("/{morderId}/settledownstream")
    public ResponseEntity<Result<YingSettleDownstream>> create(
            @RequestBody @Validated(CreateGroup.class) YingSettleDownstream yingSettleDownstream
    ) {
        yingSettleService.createDownstream(yingSettleDownstream);
        return Result.ok(yingSettleDownstream);
    }

    /**
     * 更新下游结算
     *
     * @return
     */
    @Transactional(readOnly = false)
    @PutMapping("/{morderId}/settledownstream/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) YingSettleDownstream yingSettleDownstream
    ) {
        assert (yingSettleDownstream.getOrderId() == morderId);
        int rtn = yingSettleService.updateDownstream(yingSettleDownstream);
        if (rtn != 1) {
            logger.error("更新失败: {}", yingSettleDownstream);
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }

        return Result.ok(1);
    }

    /**
     * 删除下游结算
     *
     * @return
     */
    @Transactional(readOnly = false)
    @DeleteMapping("/{morderId}/settledownstream/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int rtn = yingSettleService.deleteDownstream(id);
        if (rtn != 1) {
            return Result.error(4001, "删除失败", HttpStatus.NOT_FOUND);
        }

        return Result.ok(1);
    }

}
