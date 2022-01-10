package com.haven.dao;

import com.github.pagehelper.Page;
import com.haven.entry.QueryPageBean;
import com.haven.pojo.TravelItem;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface TravelItemDao {
    void add(TravelItem travelItem);

    Page findPage(@Param("quire") String quire);

    ArrayList<TravelItem> findAll();

    ArrayList<TravelItem> findAllByGroupId(Integer id);
}
