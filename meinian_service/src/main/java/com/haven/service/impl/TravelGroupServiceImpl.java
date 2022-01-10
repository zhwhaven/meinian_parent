package com.haven.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.haven.dao.TravelGroupDao;
import com.haven.entry.QueryPageBean;
import com.haven.pojo.TravelGroup;
import com.haven.service.TravelGroupService;
import com.haven.service.TravelItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service(interfaceClass =TravelGroupService.class )
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {
    @Autowired
    TravelGroupDao travelGroupDao;
    @Override
    public void add(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelGroupDao.add(travelGroup);
        Integer groupid = travelGroup.getId();
        List<Integer> idlist = Arrays.stream(travelItemIds).collect(Collectors.toList());
        int i=travelGroupDao.setGroupAndItem(groupid,idlist);

    }

    @Override
    public Page findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page page=travelGroupDao.findPage(queryPageBean.getQueryString());
        return page;
    }

    @Override
    public List<TravelGroup> findAll() {
        Page page = travelGroupDao.findPage("");
        List result = page.getResult();
        for (Object o : result) {
            System.out.println(o);
        }
        return result;
    }
}
