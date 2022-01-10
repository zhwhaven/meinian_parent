package com.haven.dao;

import com.haven.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    void add(Order order);

    Order findByMessage(Order order);

    Integer countOrderByTodayTime(String reportDate);

    Integer countVisitByTodayTime(String reportDate);

    Integer countOrderByWeekandMonthTime(@Param("start") String start,@Param("end") String end);

    Integer countVisitByWeekandMonthTime(@Param("start") String start,@Param("end") String end);

    List<Map> findHotSetmeal();
}
