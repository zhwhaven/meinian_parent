package com.haven.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.haven.constant.MessageConstant;
import com.haven.entry.Result;
import com.haven.service.MemberService;
import com.haven.service.ReportService;
import com.haven.service.SetmealService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {
   @Reference
   MemberService memberService;
   @Reference
   SetmealService setmealService;
   @Reference
   ReportService reportService;
    @RequestMapping("/getMemberReport.do")
   public Result getMemberReport(){

        try {
            Map map=memberService.getMemberReport();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);

        }
   
    }
    @RequestMapping("/getSetmealReport.do")
    public Result getSetmealReport(){
        try {
            Map map=setmealService.getSetmealReport();
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);

        }

    }

    @RequestMapping("/getBusinessReportData.do")
    public Result getBusinessReportData(){
        try {
            Map map=reportService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);

        }
    }
    @RequestMapping("exportBusinessReport.do")
    public void exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        Map map=reportService.getBusinessReportData();
        String reportDate =String.valueOf( map.get("reportDate"));
        String todayNewMember =String.valueOf( map.get("todayNewMember"));
        String totalMember =String.valueOf( map.get("totalMember"));
        String thisWeekNewMember =String.valueOf(map.get("thisWeekNewMember"));
        String thisMonthNewMember =String.valueOf( map.get("thisMonthNewMember"));
        String todayOrderNumber =String.valueOf( map.get("todayOrderNumber"));
        String todayVisitsNumber =String.valueOf( map.get("todayVisitsNumber"));
        String thisWeekOrderNumber =String.valueOf( map.get("thisWeekOrderNumber"));
        String thisWeekVisitsNumber =String.valueOf( map.get("thisWeekVisitsNumber"));
        String thisMonthOrderNumber =String.valueOf( map.get("thisMonthOrderNumber"));
        String thisMonthVisitsNumber =String.valueOf ( map.get("thisMonthVisitsNumber"));
        List<Map> hotSetmeal = (List<Map>) map.get("hotSetmeal");
        try {
            //        获得导出的excell的文件地址
            String realPath = request.getSession().getServletContext().getRealPath("/template");
            String filePath=realPath+ File.separator+"report_template.xlsx";
            //        创建工作簿，并写入
            Workbook wb = new XSSFWorkbook(new File(filePath));
            Sheet sheet = wb.getSheetAt(0);

            Row row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);

            Row row1 = sheet.getRow(4);
            row1.getCell(5).setCellValue(todayNewMember);
            row1.getCell(7).setCellValue(totalMember);

            Row row2 = sheet.getRow(5);
            row2.getCell(5).setCellValue(thisWeekNewMember);
            row2.getCell(7).setCellValue(thisMonthNewMember);

            Row row3 = sheet.getRow(7);
            row3.getCell(5).setCellValue(todayOrderNumber);
            row3.getCell(7).setCellValue(todayVisitsNumber);

            Row row4= sheet.getRow(8);
            row4.getCell(5).setCellValue(thisWeekOrderNumber);
            row4.getCell(7).setCellValue( thisWeekVisitsNumber);

            Row row5 = sheet.getRow(9);
            row5.getCell(5).setCellValue(thisMonthOrderNumber);
            row5.getCell(7).setCellValue(thisMonthVisitsNumber);

            int rowcount=12;
            for (Map map1 : hotSetmeal) {
                String name = String.valueOf(map1.get("name")) ;
                String setmeal_count = String.valueOf(map1.get("setmeal_count"));
                String proportion = String.valueOf(map1.get("proportion"));
                Row row6 = sheet.getRow(rowcount++);
                row6.getCell(4).setCellValue(name);
                row6.getCell(5).setCellValue(setmeal_count);
                row6.getCell(6).setCellValue(proportion);
            }

//            返回文件到服务器
            ServletOutputStream outputStream = response.getOutputStream();
//            以附件的形式下载
            response.setHeader("content-Disposition","attachment;filename=report.xlsx");

//            返回类型
            response.setContentType("application/vnd.ms-excel");

            wb.write(outputStream);

            outputStream.flush();
            outputStream.close();
            wb.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}
