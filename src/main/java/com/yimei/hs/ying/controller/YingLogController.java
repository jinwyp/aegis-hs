package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.YingEntityType;
import com.yimei.hs.ying.dto.PageYingLogDTO;
import com.yimei.hs.ying.entity.YingLog;
import com.yimei.hs.ying.service.YingLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/27.
 */
@RestController
@RequestMapping("/api/ying")
public class YingLogController {

    @Autowired
    YingLogService yingLogService;

    /**
     * 查询日志分页
     * @param morderId
     * @param yingEntityType
     * @param pageYingLogDTO
     * @return
     */
    @GetMapping("/{morderId}/log/{entityType}")
    public ResponseEntity<PageResult<YingLog>> getPage(
            @PathVariable("morderId") Long morderId,
            @PathVariable("entityType") YingEntityType yingEntityType,
            PageYingLogDTO pageYingLogDTO
    ) {
        Page<YingLog> page = yingLogService.getPage(pageYingLogDTO);
        return PageResult.ok(page);
    }

    /**
     * 创建日志
     * @param log
     * @return
     */
    @PostMapping("/{morderId}/log/{entityType}")
    public ResponseEntity<Result<YingLog>> create(
            @RequestBody YingLog log
    ) {
        int rtn = yingLogService.create(log);
        return Result.ok(log);
    }

    /**
     * 查询日志
     * @param morderId
     * @param yingEntityType
     * @param id
     * @return
     */
    @GetMapping("/orderId/log/{entityType}/{id}")
    public ResponseEntity<Result<YingLog>> findOne(
            @PathVariable("morderId") Long morderId,
            @PathVariable("entityType") YingEntityType yingEntityType,
            @PathVariable("id") Long id
    ) {
        YingLog log = yingLogService.findOne(id);
        return Result.ok(log);
    }
}
