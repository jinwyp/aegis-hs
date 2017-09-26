package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.ying.entity.YingSettleUpstream;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.ying.dto.PageYingSettleUpstreamDTO;
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
public class YingSettleUpstreamController {

    private static final Logger logger = LoggerFactory.getLogger(YingSettleUpstreamController.class);


    @Autowired
    private YingSettleService yingSettleService;

    /**
     * 获取所有huikuan
     *
     * @return
     */
    @GetMapping("/{orderId}/settleupstream")
    public ResponseEntity<PageResult<YingSettleUpstream>> list(@PathVariable("orderId") long orderId,PageYingSettleUpstreamDTO pageYingSettleUpstreamDTO) {

        pageYingSettleUpstreamDTO.setOrderId(orderId);
        return PageResult.ok(yingSettleService.getPageUpstream(pageYingSettleUpstreamDTO));
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/settleupstream/{id}")
    public ResponseEntity<Result<YingSettleUpstream>> read(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {

        YingSettleUpstream settleUpstream = yingSettleService.findUpstream(id);
        if (settleUpstream == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(settleUpstream);
        }
    }

    /**
     * 创建huikuan
     *
     * @return
     */
    @Transactional(readOnly = false)
    @PostMapping("/{orderId}/settleupstream")
    public ResponseEntity<Result<YingSettleUpstream>> create(@PathVariable("orderId") long orderId, @RequestBody YingSettleUpstream yingSettleUpstream) {
        yingSettleUpstream.setOrderId(orderId);
        yingSettleService.createUpstream(yingSettleUpstream);
        return Result.ok(yingSettleUpstream);
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @Transactional(readOnly = false)
    @PutMapping("/{orderId}/settleupstream/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) YingSettleUpstream yingSettleUpstream
    ) {
//        assert (orderId == yingSettleUpstream.getOrderId());
        yingSettleUpstream.setId(id);
        int rtn = yingSettleService.updateUpstream(yingSettleUpstream);
        if (rtn != 1) {
            return Result.error(4001, "更新失败");
        }
        return Result.ok(1);
    }
}
