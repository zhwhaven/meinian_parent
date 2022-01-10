package com.haven.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.haven.dao.MemberDao;
import com.haven.pojo.Member;
import com.haven.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberDao memberDao;
    @Override
    public Map getMemberReport() {
        //        months
//        memberCount
        //        获取前十二个月的时间
        Map map=new HashMap();
        List<String> months=new ArrayList<>();
        List<Integer> memberCount=new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
        for (int i = 0; i <12 ; i++) {
            calendar.add(Calendar.MONTH,1);
            String month = sdf.format(calendar.getTime());
            months.add(month);
        }
        for (String month : months) {
            Integer memberReport = memberDao.getMemberReport(month);
            memberCount.add(memberReport);
        }

     map.put("months",months);
     map.put("memberCount",memberCount);

        return map;
    }
}
