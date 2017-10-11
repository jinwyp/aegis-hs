package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.EntityType;
import com.yimei.hs.same.dto.PageLogDTO;
import com.yimei.hs.same.entity.Log;
import com.yimei.hs.same.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/27.
 */
@RestController
@RequestMapping("/api/business/{businessType}")
public class LogController {

    @Autowired
    LogService logService;

    /**
     * 查询日志分页
     *
     * @param morderId
     * @param entityType
     * @param pageLogDTO
     * @return
     */
    @GetMapping("/{morderId}/log/{entityType}")
    public ResponseEntity<Result<Page<Log>>> getPage(
            @PathVariable("morderId") Long morderId,
            @PathVariable("entityType") EntityType entityType,
            PageLogDTO pageLogDTO
    ) {
        Page<Log> page = logService.getPage(pageLogDTO);
        return Result.ok(page);
    }

    /**
     * 创建日志
     *
     * @param log
     * @return
     */
    @PostMapping("/{morderId}/log/{entityType}")
    public ResponseEntity<Result<Log>> create(
            @RequestBody Log log
    ) {
        int rtn = logService.create(log);
        return Result.ok(log);
    }

    /**
     * 查询日志
     *
     * @param morderId
     * @param entityType
     * @param id
     * @return
     */
    @GetMapping("/orderId/log/{entityType}/{id}")
    public ResponseEntity<Result<Log>> findOne(
            @PathVariable("morderId") Long morderId,
            @PathVariable("entityType") EntityType entityType,
            @PathVariable("id") Long id
    ) {
        Log log = logService.findOne(id);
        return Result.ok(log);
    }
}
