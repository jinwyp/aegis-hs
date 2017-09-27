package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.ext.annotation.CurrentUser;
import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.ying.entity.YingFayun;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.ying.dto.PageYingFayunDTO;
import com.yimei.hs.ying.service.YingFayunService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/ying")
@RestController
@Logined
public class YingFayunController {

    private static final Logger logger = LoggerFactory.getLogger(YingFayunController.class);


    @Autowired
    YingFayunService yingFayunService;

    /**
     * 获取所有fayun
     *
     * @return
     */
    @GetMapping("/{morderId}/fayuns")
    public ResponseEntity<PageResult<YingFayun>> list(
            @CurrentUser User user,
            @PathVariable("morderId") Long morderId,
            PageYingFayunDTO pageYingFayunDTO) {
        pageYingFayunDTO.setOrderId(morderId);
        Page<YingFayun> page = yingFayunService.getPage(pageYingFayunDTO);
        return PageResult.ok(page);
    }

    /**
     * 获取fayun
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/fayuns/{id}")
    public ResponseEntity<Result<YingFayun>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id) {

        YingFayun fayun = yingFayunService.findOne(id);
        if (fayun == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(fayun);
        }
    }

    /**
     * 创建fayun
     *
     * @return
     */
    @PostMapping("/{morderId}/fayuns")
    @Transactional(readOnly =  false)
    public ResponseEntity<Result<YingFayun>> create(
            @PathVariable("morderId") Long morderId,
            @RequestBody @Validated(CreateGroup.class) YingFayun yingFayun
    ) {

        // todo 依据上下游发运方式， 校验yingFayun

        yingFayun.setOrderId(morderId);
        yingFayunService.create(yingFayun);
        logger.info("created fayn: {}", yingFayun);
        return Result.ok(yingFayun);
    }

    /**
     * 更新fayun
     *
     * @return
     */
    @PutMapping("/{morderId}/fayuns/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>>update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id,
            @RequestBody @Validated(UpdateGroup.class) YingFayun yingFayun) {
        yingFayun.setId(id);
        int cnt = yingFayunService.update(yingFayun);
        if (cnt != 1) {
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }

    /**
     * 逻辑删除
     * @param morderId
     * @param id
     * @return
     */
    @DeleteMapping("/{morderId}/fayuns/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>>update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id
    ) {
        int cnt = yingFayunService.delete(morderId, id);
        if (cnt != 1) {
            return Result.error(4001, "删除失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }


    // 发运统计
    @Data
    private static class FayunStat {
    }

    /**
     *  发运统计
     */
    @GetMapping("/{morderId}/fayuns-stat")
    public ResponseEntity<Result<FayunStat>> stat(
            @PathVariable("morderId") Long morderId
    ) {
        return Result.ok(null);
    }

}
