package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.cang.entity.CangAnalysisData;
import com.yimei.hs.ying.entity.YingAnalysisData;
import com.yimei.hs.same.service.DataAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/business")
@RestController
@Logined
public class DataAnalysisController {

    private static final Logger logger = LoggerFactory.getLogger(DataAnalysisController.class);

    @Autowired
    DataAnalysisService dataAnalysisService;

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
        YingAnalysisData bail = dataAnalysisService.findOneYing(morderId,hsId);
        if (bail == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(bail);
        }
    }


    /**
     *
     * @param morderId
     * @return
     */
    @GetMapping("/ying/{morderId}/analysis")
    public ResponseEntity<Result<List<YingAnalysisData>>> readYingList(
            @PathVariable("morderId") Long morderId
    ) {
        List<YingAnalysisData> yingAnalysisData = dataAnalysisService.findYingList(morderId);
        if (yingAnalysisData == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(yingAnalysisData);
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
        CangAnalysisData cangAnalysisData = dataAnalysisService.findOneCang(hsId,morderId);
        if (cangAnalysisData == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(cangAnalysisData);
        }
    }


    /**
     *
     * @param morderId
     * @return
     */
    @GetMapping("/cang/{morderId}/analysis")
    public ResponseEntity<Result<List<CangAnalysisData>>> readCangList(
            @PathVariable("morderId") Long morderId
    ) {
        List<CangAnalysisData> cangAnalysisData = dataAnalysisService.findCangList(morderId);
        if (cangAnalysisData == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(cangAnalysisData);
        }
    }


    /**
     *
     * @param morderId
     * @return
     */
    @GetMapping("/ying/analysis/{morderId}")
    public ResponseEntity<Result<List<YingAnalysisData>>> readYingListPartsOne(
            @PathVariable("morderId") Long morderId
    ) {
        List<YingAnalysisData> yingAnalysisData = dataAnalysisService.findYingPartsOneList(morderId);
        if (yingAnalysisData == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(yingAnalysisData);
        }
    }
}
