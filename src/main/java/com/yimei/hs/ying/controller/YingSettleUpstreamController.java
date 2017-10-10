package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.dto.PageYingSettleUpstreamDTO;
import com.yimei.hs.ying.entity.YingSettleUpstream;
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
     * 获取上游结算 - 分页
     *
     * @return
     */
    @GetMapping("/{morderId}/settleupstream")
    public ResponseEntity<Result<Page<YingSettleUpstream>>> list(
            @PathVariable("morderId") Long morderId,
            PageYingSettleUpstreamDTO pageYingSettleUpstreamDTO) {

        pageYingSettleUpstreamDTO.setOrderId(morderId);
        return Result.ok(yingSettleService.getPageUpstream(pageYingSettleUpstreamDTO));
    }

    /**
     * 获取上游结算
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/settleupstream/{id}")
    public ResponseEntity<Result<YingSettleUpstream>> read(
            @PathVariable("morderId") Long morderId,
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
     * 创建上游结算
     *
     * @return
     */
    @Transactional(readOnly = false)
    @PostMapping("/{morderId}/settleupstream")
    public ResponseEntity<Result<YingSettleUpstream>> create(
            @PathVariable("morderId") Long morderId,
            @RequestBody @Validated(CreateGroup.class) YingSettleUpstream yingSettleUpstream) {
        yingSettleUpstream.setOrderId(morderId);
        int rtn = yingSettleService.createUpstream(yingSettleUpstream);
        if (rtn != 1) {
            logger.error("创建失败: {}", yingSettleUpstream);
            return Result.error(4001, "创建失败");
        }
        return Result.ok(yingSettleUpstream);
    }

    /**
     * 更新上游结算
     *
     * @return
     */
    @Transactional(readOnly = false)
    @PutMapping("/{morderId}/settleupstream/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) YingSettleUpstream yingSettleUpstream
    ) {
//        assert (orderId == yingSettleUpstream.getOrderId());
        yingSettleUpstream.setId(id);
        int rtn = yingSettleService.updateUpstream(yingSettleUpstream);
        if (rtn != 1) {
            logger.error("更新失败: {}", yingSettleUpstream);
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }

    /**
     * 删除上游结算
     *
     * @return
     */
    @Transactional(readOnly = false)
    @DeleteMapping("/{morderId}/settleupstream/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int rtn = yingSettleService.deleteUpstream(id);
        if (rtn != 1) {
            logger.error("删除失败: {}", id);
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }

}
