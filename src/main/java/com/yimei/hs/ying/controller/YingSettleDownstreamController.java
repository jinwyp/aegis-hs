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
     * 获取所有huikuan
     *
     * @return
     */
    @GetMapping("/{orderId}/settledownstream")
    public ResponseEntity<PageResult<YingSettleDownstream>> list(
            @PathVariable("orderId") Long orderId,
            PageYingSettleDownstreamDTO pageYingSettleDownstreamDTO
    ) {
        pageYingSettleDownstreamDTO.setOrderId(orderId);
        return PageResult.ok(yingSettleService.getPageDownstream(pageYingSettleDownstreamDTO));
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/settledownstream/{id}")
    public ResponseEntity<Result<YingSettleDownstream>> read(
            @PathVariable("orderId") long orderId,
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
     * 创建huikuan
     *
     * @return
     */
    @Transactional(readOnly = false)
    @PostMapping("/{orderId}/settledownstream")
    public ResponseEntity<Result<YingSettleDownstream>> create(
            @RequestBody @Validated(CreateGroup.class) YingSettleDownstream yingSettleDownstream
    ) {
        yingSettleService.createDownstream(yingSettleDownstream);
        return Result.ok(yingSettleDownstream);
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @Transactional(readOnly = false)
    @PutMapping("/{orderId}/settledownstream/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) YingSettleDownstream yingSettleDownstream
    ) {
        assert (yingSettleDownstream.getOrderId() == orderId);
        int rtn = yingSettleService.updateDownstream(yingSettleDownstream);
        if (rtn != 1) {
            return Result.error(4001, "更新失败");
        }

        return Result.ok(1);
    }
}
