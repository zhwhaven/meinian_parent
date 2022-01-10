package com.haven.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.haven.dao.SetmealDao;
import com.haven.entry.QueryPageBean;
import com.haven.pojo.Setmeal;
import com.haven.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealDao setmealDao;
    @Override
    public void add(List<Integer> list, Setmeal setmeal) {
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        setmealDao.setSetmealAndGroup(setmealId,list);
    }

    @Override
    public Page findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
       Page page= setmealDao.findPage(queryPageBean.getQueryString());
        return page;
    }

    @Override
    public Page getSetmeal() {
        Page page= setmealDao.findPage("");
        return page;
    }

    @Override
    public Setmeal findById(Integer id) {
        Setmeal setmeal=setmealDao.findById(id);
        return setmeal;
    }

    @Override
    public Map getSetmealReport() {
//setmealNames setmealCount
        Map mapp=new HashMap();
        List<String> setmealNames=new ArrayList<>();
       List<Map> setmealCount= setmealDao.getSetmealReport();
        for (Map map : setmealCount) {
            String name = (String) map.get("name");
            setmealNames.add(name);
        }
        mapp.put("setmealNames",setmealNames);
        mapp.put("setmealCount",setmealCount);
        return mapp;
    }

}
