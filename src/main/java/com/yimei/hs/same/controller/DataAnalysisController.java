package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.support.LocalDateSerializer;
import com.yimei.hs.cang.entity.CangAnalysisData;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.entity.ExportExcelDate;
import com.yimei.hs.same.entity.ExportExcelUpstreamPressure;
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

//    @RequestMapping("/{businessType}/analysis/exportExcel/{orderId}/{hsId}")
    public void doExportTradeListExcel(@PathVariable(value = "orderId") long orderId,
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


    /**
     *  获取上游占压
     * @param orderId
     * @param hsId
     * @param businessType
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("/{businessType}/analysis/exportExcelUpstreamPressure/{orderId}/{hsId}")
    public void doExportUpstreamPressureToExcel(@PathVariable(value = "orderId") long orderId,
                                           @PathVariable(value = "hsId") long hsId,
                                           @PathVariable(value = "businessType") BusinessType businessType,
                                           HttpServletResponse response,
                                           HttpServletRequest request) throws IOException {


        HSSFWorkbook wb = new HSSFWorkbook();
        String filename = "上游资金占压";
        HSSFSheet sheet = wb.createSheet(filename);
        HSSFRow row = sheet.createRow(0);
        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        sheet.setVerticallyCenter(true);
        sheet.setHorizontallyCenter(true);
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 8000);
        sheet.setColumnWidth(4, 8000);
        sheet.setColumnWidth(5, 8500);
        sheet.setColumnWidth(6, 4500);
        sheet.setColumnWidth(7, 8000);
        sheet.setColumnWidth(8, 2500);
        sheet.setColumnWidth(9, 2000);
        sheet.setColumnWidth(10, 8000);
        sheet.setColumnWidth(11, 8000);
        sheet.setColumnWidth(12, 2500);
        sheet.setColumnWidth(13, 2500);
        sheet.setColumnWidth(14, 10000);
        String[] excelHeader = {"事业部", "Broker团队", "业务类型", "账务公司", "一级客户名称", "二级客户名称", "船名", "付款方式", "资金占压总额", "银行贷款额度（外部融资）", "自有资金占压", "未开票金额", "备注", "备注","业务线"};
        for (int i = 0; i < excelHeader.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader[i]);
            cell.setCellStyle(style);
        }
        List<ExportExcelUpstreamPressure> exportExcelDateList = dataAnalysisService.exportUpstreamPressureToExcel(orderId, hsId,businessType);


        for (int i = 0; i < exportExcelDateList.size(); i++) {
            row = sheet.createRow(i + 1);
            ExportExcelUpstreamPressure exportExcelDate = exportExcelDateList.get(i);
            row.createCell(0).setCellValue((exportExcelDate.getDeptName() == null ? "" : exportExcelDate.getDeptName()));
            row.createCell(1).setCellValue((exportExcelDate.getTeamName() == null ? "" : exportExcelDate.getTeamName()));
            row.createCell(2).setCellValue((exportExcelDate.getBusinessType() == null ? "" : exportExcelDate.getBusinessType().value));
            row.createCell(3).setCellValue((exportExcelDate.getAccoutCompanyName() == null ? "" : exportExcelDate.getAccoutCompanyName()));
            row.createCell(4).setCellValue((exportExcelDate.getUpStreamPartyName() == null ? "" : exportExcelDate.getUpStreamPartyName()));
            row.createCell(5).setCellValue((exportExcelDate.getPartName() == null ? "" : exportExcelDate.getPartName()));
            row.createCell(6).setCellValue((exportExcelDate.getShipName() == null ? "" : exportExcelDate.getShipName()));
            row.createCell(7).setCellValue((exportExcelDate.getPayMode() == null ? "" : exportExcelDate.getPayMode()));
            row.createCell(8).setCellValue((exportExcelDate.getPressureAmountOfPrice() == null ? "" : exportExcelDate.getPressureAmountOfPrice().toString()));
            row.createCell(9).setCellValue(exportExcelDate.getBankDebtPrice() == null ? "" : exportExcelDate.getBankDebtPrice().toString());
            row.createCell(10).setCellValue((exportExcelDate.getOwnerCapitalPressure() == null ? "" : exportExcelDate.getOwnerCapitalPressure().toString()));
            row.createCell(11).setCellValue((exportExcelDate.getUnInvoicePrice()== null ? "" : exportExcelDate.getUnInvoicePrice().toString()));
            row.createCell(12).setCellValue((exportExcelDate.getMemo1() == null ? "" : exportExcelDate.getMemo1()));
            row.createCell(13).setCellValue((exportExcelDate.getMemo2() == null ? "" : exportExcelDate.getMemo2()));
            row.createCell(14).setCellValue((exportExcelDate.getLine() == null ? "" : exportExcelDate.getLine()));

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

    /**
     *  获取下游
     * @param orderId
     * @param hsId
     * @param businessType
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("/{businessType}/analysis/exportExcel/{orderId}/{hsId}")
    public void doExportBuyyerPressureToExcel(@PathVariable(value = "orderId") long orderId,
                                                @PathVariable(value = "hsId") long hsId,
                                                @PathVariable(value = "businessType") BusinessType businessType,
                                                HttpServletResponse response,
                                                HttpServletRequest request) throws IOException {


        HSSFWorkbook wb = new HSSFWorkbook();
        String filename = "上游资金占压";
        HSSFSheet sheet = wb.createSheet(filename);
        HSSFRow row = sheet.createRow(0);
        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        sheet.setVerticallyCenter(true);
        sheet.setHorizontallyCenter(true);
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 8000);
        sheet.setColumnWidth(4, 8000);
        sheet.setColumnWidth(5, 8500);
        sheet.setColumnWidth(6, 4500);
        sheet.setColumnWidth(7, 8000);
        sheet.setColumnWidth(8, 2500);
        sheet.setColumnWidth(9, 2000);
        sheet.setColumnWidth(10, 8000);
        sheet.setColumnWidth(11, 8000);
        sheet.setColumnWidth(12, 2500);
        sheet.setColumnWidth(13, 2500);
        sheet.setColumnWidth(14, 10000);
        String[] excelHeader = {"事业部", "Broker团队", "业务类型", "账务公司", "一级客户名称", "二级客户名称","终端", "船名", "资金占压总额", "已结算未回款", "未结算", "预付款", "备注", "备注","业务线"};
        for (int i = 0; i < excelHeader.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader[i]);
            cell.setCellStyle(style);
        }
     ExportExcelUpstreamPressure exportExcelUpstreamPressure = dataAnalysisService.exportDwonstreamPressureToExcel(orderId, hsId,businessType);

            row = sheet.createRow(  1);
            row.createCell(0).setCellValue((exportExcelUpstreamPressure.getDeptName() == null ? "" : exportExcelUpstreamPressure.getDeptName()));
            row.createCell(1).setCellValue((exportExcelUpstreamPressure.getTeamName() == null ? "" : exportExcelUpstreamPressure.getTeamName()));
            row.createCell(2).setCellValue((exportExcelUpstreamPressure.getBusinessType() == null ? "" : exportExcelUpstreamPressure.getBusinessType().value));
            row.createCell(3).setCellValue((exportExcelUpstreamPressure.getAccoutCompanyName() == null ? "" : exportExcelUpstreamPressure.getAccoutCompanyName()));
            row.createCell(4).setCellValue((exportExcelUpstreamPressure.getDwonStreamPartyName() == null ? "" : exportExcelUpstreamPressure.getDwonStreamPartyName()));
            row.createCell(5).setCellValue((exportExcelUpstreamPressure.getPartName() == null ? "" : exportExcelUpstreamPressure.getPartName()));
            row.createCell(6).setCellValue((exportExcelUpstreamPressure.getTerminalClientName() == null ? "" : exportExcelUpstreamPressure.getTerminalClientName()));
            row.createCell(7).setCellValue((exportExcelUpstreamPressure.getShipName() == null ? "" : exportExcelUpstreamPressure.getShipName()));
            row.createCell(8).setCellValue((exportExcelUpstreamPressure.getPressureAmountOfPrice() == null ? "" : exportExcelUpstreamPressure.getPressureAmountOfPrice().toString()));
            row.createCell(9).setCellValue(exportExcelUpstreamPressure.getSettledDownstreamHuikuanMoneny() == null ? "" : exportExcelUpstreamPressure.getSettledDownstreamHuikuanMoneny().toString());
            row.createCell(10).setCellValue((exportExcelUpstreamPressure.getUnsettleSellerMoneyAmount() == null ? "" : exportExcelUpstreamPressure.getUnsettleSellerMoneyAmount().toString()));
            row.createCell(11).setCellValue((exportExcelUpstreamPressure.getPrePayment()== null ? "" : exportExcelUpstreamPressure.getPrePayment().toString()));
            row.createCell(12).setCellValue((exportExcelUpstreamPressure.getMemo1() == null ? "" : exportExcelUpstreamPressure.getMemo1()));
            row.createCell(13).setCellValue((exportExcelUpstreamPressure.getMemo2() == null ? "" : exportExcelUpstreamPressure.getMemo2()));
            row.createCell(14).setCellValue((exportExcelUpstreamPressure.getLine() == null ? "" : exportExcelUpstreamPressure.getLine()));

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
