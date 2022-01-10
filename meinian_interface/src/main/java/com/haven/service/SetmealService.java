package com.haven.service;

import com.github.pagehelper.Page;
import com.haven.entry.QueryPageBean;
import com.haven.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    void add(List<Integer> list, Setmeal setmeal);

    Page findPage(QueryPageBean queryPageBean);

    Page getSetmeal();

    Setmeal findById(Integer id);

    Map getSetmealReport();
}
