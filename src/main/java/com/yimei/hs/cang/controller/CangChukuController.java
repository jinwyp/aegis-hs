package com.yimei.hs.cang.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.cang.entity.CangRuku;
import com.yimei.hs.cang.entity.ChukuInfo;
import com.yimei.hs.cang.service.CangChukuService;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.cang.dto.PageCangChukuDTO;
import com.yimei.hs.cang.entity.CangChuku;
import com.yimei.hs.same.entity.CapitalPressure;
import com.yimei.hs.same.entity.OrderConfig;
import com.yimei.hs.same.service.DataAnalysisService;
import com.yimei.hs.ying.entity.AnalysisData;
import com.yimei.hs.ying.mapper.YingAnalysisDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@RestController
@RequestMapping("/api/business/{businessType}")
public class CangChukuController {

    private static final Logger logger = LoggerFactory.getLogger(CangChukuController.class);

    @Autowired
    CangChukuService cangChukuService;

    @Autowired
    DataAnalysisService dataAnalysisService;

    @GetMapping("/{morderId}/chukus")
    public ResponseEntity<Result<Page<CangChuku>>> list(
            @PathVariable("morderId") Long morderId,
            @PathVariable("businessType") BusinessType businessType,
            PageCangChukuDTO pageCangChukuDTO
    ) {
        pageCangChukuDTO.setOrderId(morderId);
        Page<CangChuku> page = cangChukuService.getPage(pageCangChukuDTO);
        return Result.ok(page);
    }

    /**
     * @param morderId
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/chukus/{id}")
    public ResponseEntity<Result<CangChuku>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("id") long id
    ) {
        CangChuku cangRuku = cangChukuService.findOne(id);
        if (cangRuku == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(cangRuku);
        }
    }

    /**
     * 创建cangRuku
     *
     * @return
     */
    @PostMapping("/{morderId}/chukus")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<CangChuku>> create(
            @PathVariable("businessType") BusinessType businessType,
            @RequestBody @Validated(CreateGroup.class) CangChuku cangRuku
    ) {
        int rtn = cangChukuService.create(cangRuku);
        if (rtn != 1) {
            logger.error("创建失败: {}", cangRuku);
            return Result.error(5001, "创建失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(cangRuku);
    }

    /**
     * 更新cangRuku
     *
     * @return
     */
    @PutMapping("/{morderId}/chukus/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("id") Long id,
            @RequestBody @Validated(UpdateGroup.class) CangChuku cangRuku
    ) {
        assert (morderId == cangRuku.getOrderId());
        cangRuku.setId(id);
        int rtn = cangChukuService.update(cangRuku);
        if (rtn != 1) {
            return Result.error(5001, "更新失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }

    /**
     * 删除cangRuku
     *
     * @return
     */
    @DeleteMapping("/{morderId}/chukus/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> delete(
            @PathVariable("morderId") Long morderId,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("id") Long id
    ) {
        int rtn = cangChukuService.delete(id);
        if (rtn != 1) {
            return Result.error(5001, "删除失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }


    /**
     * @param morderId
     * @param hsId
     * @return
     */
    @GetMapping("/{morderId}/chukusInfo/{hsId}")
    public ResponseEntity<Result<ChukuInfo>> chukuInfo(
            @PathVariable("morderId") Long morderId,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("hsId") long hsId
    ) {
        ChukuInfo chukuInfo = new ChukuInfo();
        AnalysisData analysisData3001 = dataAnalysisService.findV3001(morderId, hsId);
        AnalysisData analysisData3003 = dataAnalysisService.findV3003(morderId, hsId);
        OrderConfig analysisDataBase = dataAnalysisService.findBase(morderId, hsId);

        BigDecimal totalOutStorageMoney = analysisData3003.getTotalOutstorageMoney();
        chukuInfo.setTotalOutstorageMoney(totalOutStorageMoney);
        chukuInfo.setTotalOutstorageNum(analysisData3003.getTotalOutstorageNum());
//        汇总本核算月【入库】入库吨数 - 汇总本核算月【出库】出库吨数
        BigDecimal unchukuTotalAmount = analysisData3001.getTotalInstoragedNum().subtract(analysisData3003.getTotalOutstorageNum());
        chukuInfo.setUnChukuTotalAmount(unchukuTotalAmount);
//        "本月未出库金额" = "本月未出库吨数" * 【核算月信息】下游预估加权单价
        BigDecimal unChukuPrice = analysisDataBase.getWeightedPrice().multiply(unchukuTotalAmount);
        chukuInfo.setUnChukuTotalPrice(unChukuPrice);

        //汇总回款用途 不等于"保证金"的回款金额
        BigDecimal totalHuikuanExceptBail = dataAnalysisService.findHuikuanExceptBail(morderId, hsId);


//        本月可出库金额
//        "本月可出库金额" = 汇总回款用途 不等于"保证金"的回款金额 - "本月累计出库金额"
        BigDecimal canChukuPrice = (totalHuikuanExceptBail == null ? new BigDecimal("0.00") : totalHuikuanExceptBail.subtract(totalOutStorageMoney));

        chukuInfo.setCanChukuPrice((canChukuPrice.compareTo(BigDecimal.ZERO) == -1 ? new BigDecimal("0.00"): canChukuPrice));


        chukuInfo.setCanChukuAmount((canChukuPrice.compareTo(BigDecimal.ZERO)==-1?new BigDecimal("0.00"):canChukuPrice.divide(analysisDataBase.getWeightedPrice(),2,BigDecimal.ROUND_HALF_UP)));
        if (chukuInfo == null) {

            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(chukuInfo);
        }
    }


}
