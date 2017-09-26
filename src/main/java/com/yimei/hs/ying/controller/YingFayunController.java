package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingFayun;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.ying.dto.PageYingFayunDTO;
import com.yimei.hs.ying.service.YingFayunService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/ying")
@RestController
public class YingFayunController {

    private static final Logger logger = LoggerFactory.getLogger(YingFayunController.class);


    @Autowired
    YingFayunService yingFayunService;

    /**
     * 获取所有fayun
     *
     * @return
     */
    @GetMapping("/{orderId}/fayuns")
    public ResponseEntity<PageResult<YingFayun>> list(PageYingFayunDTO pageYingFayunDTO) {
        Page<YingFayun> page = yingFayunService.getPage(pageYingFayunDTO);
        return PageResult.ok(page);
    }

    /**
     * 获取fayun
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}fayuns/{id}")
    public ResponseEntity<Result<YingFayun>> read(
            @PathVariable("orderId") Long orderId,
            @PathVariable("id") long id) {

        YingFayun fayun = yingFayunService.findOne(id);
        if (fayun == null) {
            return Result.error(4001, "记录不存在");
        } else {
            return Result.ok(fayun);
        }
    }

    /**
     * 创建fayun
     *
     * @return
     */
    @PostMapping("/{orderId}/fayuns")
    @Transactional(readOnly =  false)
    public ResponseEntity<Result<YingFayun>> create(
            @PathVariable("orderId") long orderId,
            @RequestBody YingFayun yingFayun
    ) {
        yingFayun.setOrderId(orderId);
        yingFayunService.create(yingFayun);
        logger.info("created fayn: {}", yingFayun);
        return Result.ok(yingFayun);
    }

    /**
     * 更新fayun
     *
     * @return
     */
    @PutMapping("/{orderId}/fayuns/{id}")
    @Transactional(readOnly =  false)
    public ResponseEntity<Result<YingFayun>> update(
            @PathVariable("orderId") Long orderId,
            @PathVariable("id") Long id,
            @RequestBody YingFayun yingFayun) {
        assert (orderId == orderId);
        yingFayun.setId(id);
        int cnt = yingFayunService.update(yingFayun);
        if (cnt != 2) {
            return Result.error(4001, "更新失败");
        }
        return Result.ok(yingFayun);
    }


    // 发运统计
    @Data
    private static class FayunStat {
    }

    /**
     *  发运统计
     */
    @GetMapping("/{orderId}/fayuns-stat")
    public ResponseEntity<Result<FayunStat>> stat(
            @PathVariable("orderId") long orderId
    ) {
        return Result.ok(null);
    }

}
