package com.haven.service;

import com.haven.pojo.OrderSetting;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrderSettingService {
    List<OrderSetting> getOrderSettingByMonth(String date);

    void add(List<String[]> list);

    void editNumberByDate(OrderSetting orderSetting);
}
