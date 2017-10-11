package com.yimei.hs.cang.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.cang.service.CangChukuService;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.cang.dto.PageCangChukuDTO;
import com.yimei.hs.cang.entity.CangChuku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hary on 2017/9/15.
 */
@RestController
@RequestMapping("/api/business/{businessType}")
public class CangChukuController {

    @Autowired
    CangChukuService cangChukuService;


    @GetMapping("/{morderId}/chukus")
    public ResponseEntity<Result<Page<CangChuku>>> list(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            PageCangChukuDTO pageCangChukuDTO
    ) {
        // pageChukuDTO.setOrderId(morderId);
        Page<CangChuku> page = cangChukuService.getPage(pageCangChukuDTO);
        return Result.ok(page);
    }

}
