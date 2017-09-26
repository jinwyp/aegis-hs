package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.ying.dto.PageYingOrderConfigDTO;
import com.yimei.hs.ying.entity.YingOrderConfig;
import com.yimei.hs.ying.service.YingOrderConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/25.
 */
@RestController
@RequestMapping("/api/ying")
public class YingOrderConfigController {


    private static final Logger logger = LoggerFactory.getLogger(YingOrderController.class);

    @Autowired
    YingOrderConfigService yingOrderConfigService;


    /**
     * 获取分页数据应收订单核月配置
     *
     * @return
     */
    @GetMapping("/{orderId}/configs")
    public ResponseEntity<PageResult<YingOrderConfig>> list(@PathVariable("orderId") long orderId, PageYingOrderConfigDTO pageYingOrderConfigDTO) {
        pageYingOrderConfigDTO.setOrderId(orderId);
        return PageResult.ok(yingOrderConfigService.getPage(pageYingOrderConfigDTO));
    }

    /**
     * 创建核算月配置
     *
     * @param yingOrderConfig
     * @return
     */
    @PostMapping("/{orderId}/configs")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<YingOrderConfig>> create(
            @PathVariable("orderId") long orderId,
            @RequestBody YingOrderConfig yingOrderConfig) {

        yingOrderConfig.setOrderId(orderId);
        int rtn = yingOrderConfigService.create(yingOrderConfig);
        if (rtn != 1) {
            return Result.error(5001, "创建失败");
        } else {
            return Result.ok(yingOrderConfig);
        }
    }

    /**
     * 查询核算月数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/configs/{id}")
    public ResponseEntity<Result<YingOrderConfig>> findOne(@PathVariable("id") Long id, @PathVariable("orderId") Long orderId) {
        YingOrderConfig yingOrderConfig = yingOrderConfigService.findOne(id);

        if (yingOrderConfig == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(yingOrderConfig);
        }
    }

    @PutMapping("/{orderId}/configs/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") Long orderId,
            @PathVariable("id") Long id,
            @RequestBody @Validated(UpdateGroup.class) YingOrderConfig yingOrderConfig) {


        logger.debug("yingOrderConfig-====>" + id);
        yingOrderConfig.setId(id);
        yingOrderConfig.setOrderId(orderId);

        logger.debug("yingOrderConfig-====>" + yingOrderConfig);
        int status = yingOrderConfigService.update(yingOrderConfig);
        if (status == 1) {
            return Result.ok(1);
        } else {
            return Result.error(5003, "更新失败");
        }

    }

}

