package com.haven.service;

import com.github.pagehelper.Page;
import com.haven.entry.PageResult;
import com.haven.entry.QueryPageBean;
import com.haven.pojo.TravelItem;

import java.util.List;

public interface TravelItemService {
    void add(TravelItem travelItem);

    PageResult findPage(QueryPageBean queryPageBean);

    List<TravelItem> findAll();
}
