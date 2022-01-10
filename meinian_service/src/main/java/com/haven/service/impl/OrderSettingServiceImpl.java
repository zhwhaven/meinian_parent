package com.haven.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.haven.dao.OrderSettingDao;
import com.haven.pojo.OrderSetting;
import com.haven.service.OrderSettingService;
import com.haven.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService{
    @Autowired
    OrderSettingDao orderSettingDao;

    @Override
    public List<OrderSetting> getOrderSettingByMonth(String date) {
        System.out.println("传入的时间"+date);
        String startTime=date+"-1";
        String endTime=date+"-31";
        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = s.parse(startTime);
            Date date2 = s.parse(endTime);
//            System.out.println(date1 +"结束时间"+date2);
            Map<String,Date> map=new HashMap();
            map.put("startTime",date1);
            map.put("endTime",date2);
            List<OrderSetting> list=orderSettingDao.getOrderSettingByMonth(map);
           list.forEach((x)->{
               System.out.println("x.getOrderDate() = " + x.getOrderDate());

           });
            return list;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void add(List<String[]> list) {
        System.out.println("1111");
        try {
            list.forEach((order) -> {
                String orderDate = order[0];
                String number = order[1];
                OrderSetting orderSetting = new OrderSetting();
                orderSetting.setNumber(Integer.parseInt(number));
                orderSetting.setOrderDate(new Date(orderDate));
                Integer count = orderSettingDao.findByDate(new Date(orderDate));
                System.out.println("3333");
                if (count > 0) {
                    orderSettingDao.update(orderSetting);
                } else {
                    orderSettingDao.add(orderSetting);

                }
            });
        } finally {

        }

    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        System.out.println("11111");
        System.out.println(orderSetting.getOrderDate());
        Integer count=orderSettingDao.findByDate(orderSetting.getOrderDate());
        System.out.println("count = " + count);
        System.out.println("2222");

        if(count>0){
            orderSettingDao.update(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);

        }
    }
}
