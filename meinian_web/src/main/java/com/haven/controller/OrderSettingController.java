package com.haven.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.haven.constant.MessageConstant;
import com.haven.entry.Result;
import com.haven.pojo.OrderSetting;
import com.haven.service.OrderSettingService;
import com.haven.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    OrderSettingService orderSettingService;
    @RequestMapping("/getOrderSettingByMonth.do")
    public Result getOrderSettingByMonth(String date){
           List<OrderSetting> list=orderSettingService.getOrderSettingByMonth(date);
           List<Map> data=new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map map=new HashMap();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            data.add(map);
        }
           return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,data);
    }
    @RequestMapping("/upload.do")
    public Result upload(MultipartFile excelFile){
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            orderSettingService.add(list);

            return new Result(true,MessageConstant.UPLOAD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }
    }
    @RequestMapping("/editNumberByDate.do")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            System.out.println("进入control");
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);

        }
    }
}
