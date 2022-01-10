package com.haven.dao;

import com.github.pagehelper.Page;
import com.haven.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    //    添加套餐
    void add(Setmeal setmeal);
//   添加套餐和团游的关系
    void setSetmealAndGroup(@Param("setmealId") Integer setmealId, @Param("list") List<Integer> list);

    Page findPage(@Param("queryString") String queryString);
//查询套餐详情
    Setmeal findById(Integer id);

    List<Map> getSetmealReport();
}
