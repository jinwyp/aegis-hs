package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.support.LocalDateSerializer;
import com.yimei.hs.cang.entity.CangAnalysisData;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.entity.ExportExcelDate;
import com.yimei.hs.ying.entity.AnalysisData;
import com.yimei.hs.same.service.DataAnalysisService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/business")
@RestController

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
    @Logined
    public ResponseEntity<Result<AnalysisData>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("hsId") long hsId) {
        logger.debug("id {}",hsId);
        AnalysisData bail = dataAnalysisService.findOneYing(morderId,hsId);
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
    @Logined
    public ResponseEntity<Result<List<AnalysisData>>> readYingList(
            @PathVariable("morderId") Long morderId
    ) {
        List<AnalysisData> yingAnalysisData = dataAnalysisService.findYingList(morderId);
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
    @Logined
    public ResponseEntity<Result<AnalysisData>> readcang(
            @PathVariable("morderId") Long morderId,
            @PathVariable("hsId") long hsId
    ) {
        AnalysisData yingAnalysisData= dataAnalysisService.findOneCang(hsId,morderId);
        if (yingAnalysisData == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(yingAnalysisData);
        }
    }


    /**
     *
     * @param morderId
     * @return
     */
    @GetMapping("/cang/{morderId}/analysis")
    @Logined
    public ResponseEntity<Result<List<AnalysisData>>> readCangList(
            @PathVariable("morderId") Long morderId
    ) {
        List<AnalysisData> cangAnalysisData = dataAnalysisService.findCangList(morderId);
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
    @Logined
    public ResponseEntity<Result<List<AnalysisData>>> readYingListPartsOne(
            @PathVariable("morderId") Long morderId
    ) {
        List<AnalysisData> yingAnalysisData = dataAnalysisService.findYingPartsOneList(morderId);
        if (yingAnalysisData == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(yingAnalysisData);
        }
    }

    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping("/{businessType}/analysis/exportExcel/{orderId}/{hsId}")
    public void doExportCooperationToExcel(@PathVariable(value = "orderId") long orderId,
                                           @PathVariable(value = "hsId") long hsId,
                                           @PathVariable(value = "businessType") BusinessType businessType,
                                           HttpServletResponse response,
                                           HttpServletRequest request) throws IOException {


        HSSFWorkbook wb = new HSSFWorkbook();
        String filename = "收益计算过程";
        HSSFSheet sheet = wb.createSheet(filename);
        HSSFRow row = sheet.createRow(0);
        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        sheet.setVerticallyCenter(true);
        sheet.setHorizontallyCenter(true);
        sheet.setColumnWidth(0, 1200);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 10000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 4500);
        sheet.setColumnWidth(6, 4500);
        sheet.setColumnWidth(7, 8000);
        sheet.setColumnWidth(8, 2500);
        sheet.setColumnWidth(9, 2000);
        sheet.setColumnWidth(10, 20000);
        String[] excelHeader = {"付款日期", "付款金额（元）", "对应金额", "付款日期", "对应回款日期", "计息天数", "应计利息金额", "还款利息", "服务费", "客户/ccs还服务费", "回款日期", "回款金额", "回款方式", "贴现息"};
        for (int i = 0; i < excelHeader.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader[i]);
            cell.setCellStyle(style);
        }
        List<ExportExcelDate> exportExcelDateList = dataAnalysisService.exportExcel(orderId, hsId);


        for (int i = 0; i < exportExcelDateList.size(); i++) {
            row = sheet.createRow(i + 1);
            ExportExcelDate exportExcelDate = exportExcelDateList.get(i);
            row.createCell(0).setCellValue((exportExcelDate.getFukuanDate() == null ? "" : exportExcelDate.getFukuanDate().format(formatter)));
            row.createCell(1).setCellValue((exportExcelDate.getFukuanAmount() == null ? "" : exportExcelDate.getFukuanAmount().toString()));
            row.createCell(2).setCellValue((exportExcelDate.getAmount() == null ? "" : exportExcelDate.getAmount().toString()));
            row.createCell(3).setCellValue((exportExcelDate.getPayDate() == null ? "" : exportExcelDate.getPayDate().format(formatter)));
            row.createCell(4).setCellValue((exportExcelDate.getHuikuanDate() == null ? "" : exportExcelDate.getHuikuanDate().format(formatter)));
            row.createCell(5).setCellValue((exportExcelDate.getTime() == null ? "" : exportExcelDate.getTime().toString()));
            row.createCell(6).setCellValue((exportExcelDate.getRate() == null ? "" : exportExcelDate.getRate().toString()));
            row.createCell(7).setCellValue((exportExcelDate.getHuankuanInterest() == null ? "" : exportExcelDate.getHuankuanInterest().toString()));
            row.createCell(8).setCellValue((exportExcelDate.getHuankuanServiceFee() == null ? "" : exportExcelDate.getHuankuanServiceFee().toString()));
            row.createCell(9).setCellValue(exportExcelDate.getHuankuanServiceFee() == null ? false : exportExcelDate.isCcs());
            row.createCell(10).setCellValue((exportExcelDate.getHuikuanTime() == null ? "" : exportExcelDate.getHuikuanTime().format(formatter)));
            row.createCell(11).setCellValue((exportExcelDate.getHuikuanAmount() == null ? "" : exportExcelDate.getHuikuanAmount().toString()));
            row.createCell(12).setCellValue((exportExcelDate.getHuikuanMode() == null ? "" : exportExcelDate.getHuikuanMode().value));
            row.createCell(13).setCellValue((exportExcelDate.getTieRate() == null ? "" : exportExcelDate.getTieRate().toString()));


        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        filename += LocalDate.now() + ".xls";
        if (request.getHeader("user-agent").toLowerCase().contains("firefox")) {
            filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
        } else {
            filename = URLEncoder.encode(filename, "UTF-8");
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        OutputStream out = response.getOutputStream();
        wb.write(out);
        out.close();
    }
}
