package com.haven.dao;

import com.haven.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberDao {
    Member findByTelephone(@Param("telephone") String telephone);

    void add(Member member);

    Integer getMemberReport(@Param("month") String month);

    Integer countNewAddByTodayTime(String reportDate);

    Integer getAllMember();

    Integer getNewAddByWeekAndMonthTime(String day);
}
