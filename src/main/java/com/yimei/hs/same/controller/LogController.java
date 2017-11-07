package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.ext.annotation.CurrentUser;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.EntityType;
import com.yimei.hs.same.dto.PageLogDTO;
import com.yimei.hs.same.entity.Log;
import com.yimei.hs.same.service.LogService;
import com.yimei.hs.same.service.OrderConfigService;
import com.yimei.hs.same.service.OrderService;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.user.mapper.UserMapper;
import com.yimei.hs.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/27.
 */
@RestController
@RequestMapping("/api/business")
public class LogController {
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    LogService logService;
    @Autowired
    UserService userService;
    @Autowired
    OrderConfigService orderConfigService;
    @Autowired
    OrderService orderService;

    /**
     * 查询日志分页
     *
     * @param morderId
     * @param entityType
     * @param pageLogDTO
     * @return
     */
    @GetMapping("/{businessType}/{morderId}/log/{entityType}")
    public ResponseEntity<Result<Page<Log>>> getPage(
            @PathVariable("morderId") Long morderId,
            @PathVariable("entityType") EntityType entityType,
            PageLogDTO pageLogDTO
    ) {
        pageLogDTO.setOrderId(morderId);
        Page<Log> pages = logService.getPage(pageLogDTO);

        for (Log log : pages.getResults()) {
            log.setOrderDesc(orderService.findOne(log.getOrderId()).getLine());
            log.setEditorDesc(userService.getUserById(log.getEditorId()).getPhone());
            log.setHsIdDesc(orderConfigService.findOne(log.getHsId()).getHsMonth());
            log.setEntityTypeDesc(log.getEntityType().name());

        }
        return Result.ok(pages);
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
    @GetMapping("/{businessType}//{morderId}/log/{entityType}/{id}")
    public ResponseEntity<Result<Log>> findOne(
            @PathVariable("morderId") Long morderId,
            @PathVariable("entityType") EntityType entityType,
            @PathVariable("id") Long id
    ) {
        Log log = logService.findOne(id);
        return Result.ok(log);
    }


    /**
     * 查询日志分页
     *
     * @return
     */
    @GetMapping("/logs")
    public ResponseEntity<Result<Page<Log>>> getALlPage(@CurrentUser User user,
                                                        PageLogDTO pageLogDTO
    ) {
        Page<Log> pages = logService.getPage(pageLogDTO);

        for (Log log : pages.getResults()) {
            log.setEntityTypeDesc(EntityType.class.);
        }
        return Result.ok(pages);
    }}
