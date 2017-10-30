package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.cang.dto.CangAnalysisData;
import com.yimei.hs.cang.service.CangAnalysisService;
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
@RequestMapping("/api/business")
@RestController
@Logined
public class YingDataAnalysisController {

    private static final Logger logger = LoggerFactory.getLogger(YingDataAnalysisController.class);

    @Autowired
    YingDataAnalysisService yingDataAnalysisService;

    @Autowired
    CangAnalysisService cangAnalysisService;


    /**
     * 获取bail
     *
     * @param
     * @return
     */
    @GetMapping("/ying/{morderId}/analysis/{hsId}")
    public ResponseEntity<Result<YingAnalysisData>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("hsId") long hsId) {
        logger.debug("id {}",hsId);
        YingAnalysisData bail = yingDataAnalysisService.findOneYing(morderId,hsId);
        if (bail == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(bail);
        }
    }
    /**
     *
     * @param morderId
     * @param hsId
     * @return
     */
    @GetMapping("/cang/{morderId}/analysis/{hsId}")
    public ResponseEntity<Result<CangAnalysisData>> readcang(
            @PathVariable("morderId") Long morderId,
            @PathVariable("hsId") long hsId
    ) {
        CangAnalysisData cangAnalysisData = yingDataAnalysisService.findOneCang(hsId,morderId);
        if (cangAnalysisData == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(cangAnalysisData);
        }
    }


}
