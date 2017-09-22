package com.yimei.hs.controller.ying;

import com.yimei.hs.entity.YingSettleUpstream;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import com.yimei.hs.entity.dto.ying.PageYingSettleUpstreamDTO;
import com.yimei.hs.service.ying.YingSettleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/21.
 */
@RestController
@RequestMapping("/api/ying")
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
    public ResponseEntity<PageResult<YingSettleUpstream>> list(PageYingSettleUpstreamDTO pageYingSettleUpstreamDTO) {
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
    @PostMapping("/{orderId}/settleupstream")
    public ResponseEntity<Result<YingSettleUpstream>> create(YingSettleUpstream yingSettleUpstream) {
        yingSettleService.createUpstream(yingSettleUpstream);
        return Result.ok(yingSettleUpstream);
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @PutMapping("/{orderId}/settleupstream/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        return Result.ok(1);
    }
}
