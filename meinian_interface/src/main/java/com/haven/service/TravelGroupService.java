package com.haven.service;

import com.github.pagehelper.Page;
import com.haven.entry.QueryPageBean;
import com.haven.pojo.TravelGroup;

import java.util.List;

public interface TravelGroupService {
    void add(Integer[] travelItemIds, TravelGroup travelGroup);

    Page findPage(QueryPageBean queryPageBean);

    List<TravelGroup> findAll();
}
