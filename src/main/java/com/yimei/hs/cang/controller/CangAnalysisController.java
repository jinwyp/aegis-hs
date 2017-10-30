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
@RequestMapping("/api/business")
public class CangAnalysisController {

    private static final Logger logger = LoggerFactory.getLogger(CangAnalysisController.class);




}
