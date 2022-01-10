package com.haven.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.haven.dao.TravelItemDao;
import com.haven.entry.PageResult;
import com.haven.entry.QueryPageBean;
import com.haven.pojo.TravelItem;
import com.haven.service.TravelItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass =TravelItemService.class )
@Transactional
public class TravelItemServiceImpl implements TravelItemService {
    @Autowired
    TravelItemDao travelItemDao;
    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page page=travelItemDao.findPage(queryPageBean.getQueryString());
        PageResult pageResult=new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public List<TravelItem> findAll() {
        List<TravelItem> list=travelItemDao.findAll();
        return list;
    }


}
