package com.yimei.hs.cang.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.cang.dto.CangAnalysisData;
import com.yimei.hs.cang.dto.PageCangChukuDTO;
import com.yimei.hs.cang.entity.CangChuku;
import com.yimei.hs.cang.service.CangAnalysisService;
import com.yimei.hs.cang.service.CangChukuService;
import com.yimei.hs.enums.BusinessType;
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
@RestController
@RequestMapping("/api/business/cang")
public class CangAnalysisController {

    private static final Logger logger = LoggerFactory.getLogger(CangAnalysisController.class);

    @Autowired
    CangAnalysisService cangAnalysisService;
    /**
     *
     * @param morderId
     * @param hsId
     * @return
     */
    @GetMapping("/{morderId}/analysis/{hsId}")
    public ResponseEntity<Result<CangAnalysisData>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("hsId") long hsId
    ) {
        CangAnalysisData cangAnalysisData = cangAnalysisService.findOne(hsId,morderId);
        if (cangAnalysisData == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(cangAnalysisData);
        }
    }



}
