package com.haven.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.haven.dao.MemberDao;
import com.haven.dao.OrderDao;
import com.haven.service.ReportService;
import com.haven.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    MemberDao memberDao;
    @Autowired
    OrderDao orderDao;
    @Override
    public Map getBusinessReportData() {
//        reportDate:null, // 日期
//                todayNewMember :0, // 新增会员数
//                totalMember :0,// 总会员数
//                thisWeekNewMember :0,// 本周新增会员数
//                thisMonthNewMember :0,// 本月新增会员数
//                todayOrderNumber :0,// 今日预约数
//                todayVisitsNumber :0,// 今日出游数
//                thisWeekOrderNumber :0,// 本周预约数
//                thisWeekVisitsNumber :0,// 本周出游数
//                thisMonthOrderNumber :0,// 本月预约数
//                thisMonthVisitsNumber :0,// 本月出游数
//                hotSetmeal :[
//        {name:'海南7天6晚游套餐',setmeal_count:200,proportion:0.222},
//        {name:'深圳3天2晚游套餐',setmeal_count:200,proportion:0.222}
        try {
//            今天的日期
            String reportDate = DateUtils.parseDate2String(DateUtils.getToday());
//            本周
            String monday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
            String sunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
//           本月
            String first = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
            String last=DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());

            Integer todayNewMember=memberDao.countNewAddByTodayTime(reportDate);
            Integer totalMember=memberDao.getAllMember();
            Integer thisWeekNewMember=memberDao.getNewAddByWeekAndMonthTime(monday);
            Integer thisMonthNewMember=memberDao.getNewAddByWeekAndMonthTime(first);

            Integer todayOrderNumber=orderDao.countOrderByTodayTime(reportDate);
            Integer todayVisitsNumber=orderDao.countVisitByTodayTime(reportDate);
            Integer thisWeekOrderNumber=orderDao.countOrderByWeekandMonthTime(monday,sunday);
            Integer thisWeekVisitsNumber=orderDao.countVisitByWeekandMonthTime(monday,sunday);
            Integer thisMonthOrderNumber=orderDao.countOrderByWeekandMonthTime(first,last);
            Integer thisMonthVisitsNumber=orderDao.countVisitByWeekandMonthTime(first,last);

            List<Map> hotSetmeal=orderDao.findHotSetmeal();

//            封装

            Map map=new HashMap();
            map.put("reportDate",reportDate);
            map.put("todayNewMember",todayNewMember);
            map.put("totalMember",totalMember);
            map.put("thisWeekNewMember",thisWeekNewMember);
            map.put("thisMonthNewMember",thisMonthNewMember);
            map.put("todayOrderNumber",todayOrderNumber);
            map.put("todayVisitsNumber",todayVisitsNumber);
            map.put("thisWeekOrderNumber",thisWeekOrderNumber);
            map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
            map.put("thisMonthOrderNumber",thisMonthOrderNumber);
            map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
            map.put("hotSetmeal",hotSetmeal);
            return map;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }



    }
}
