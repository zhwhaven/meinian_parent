package com.haven.dao;

import com.haven.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    List<OrderSetting> getOrderSettingByMonth(Map<String,Date> map);

    Integer findByDate(@Param("orderDate") Date orderDate);

    void update(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    OrderSetting findAllByDate(Date orderDate);
}
