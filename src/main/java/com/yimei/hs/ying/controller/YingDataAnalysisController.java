package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.ying.dto.PageYingBailDTO;
import com.yimei.hs.ying.entity.YingAnalysisData;
import com.yimei.hs.ying.entity.YingBail;
import com.yimei.hs.ying.service.YingDataAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/business/ying")
@RestController
@Logined
public class YingDataAnalysisController {

    private static final Logger logger = LoggerFactory.getLogger(YingDataAnalysisController.class);

    @Autowired
    YingDataAnalysisService yingDataAnalysisService;

    /**
     * 获取bail
     *
     * @param
     * @return
     */
    @GetMapping("/{morderId}/analysis/{hsId}")
    public ResponseEntity<Result<YingAnalysisData>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("hsId") long hsId) {
        logger.debug("id {}",hsId);
        YingAnalysisData bail = yingDataAnalysisService.findOne(morderId,hsId);
        if (bail == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(bail);
        }
    }



}
