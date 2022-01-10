package com.haven.dao;

import com.github.pagehelper.Page;
import com.haven.pojo.TravelGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelGroupDao {
    void add(TravelGroup travelGroup);

    int setGroupAndItem(@Param("groupid") Integer groupid,@Param("idlist") List<Integer> idlist);

    Page findPage(@Param("queryString") String queryString);

    List<TravelGroup> findAllBySetmealId(Integer id);
}
