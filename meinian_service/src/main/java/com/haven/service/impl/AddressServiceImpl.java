package com.haven.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.haven.dao.AddressDao;
import com.haven.entry.QueryPageBean;
import com.haven.entry.Result;
import com.haven.pojo.Address;
import com.haven.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = AddressService.class)
@Transactional
public class AddressServiceImpl implements AddressService{
    @Autowired
    AddressDao addressDao;

    @Override
    public void addAddress(Map param) {
        addressDao.addAddress(param);
    }

    @Override
    public Page findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page page=addressDao.findMaps(queryPageBean.getQueryString());
        List<Address> addresses = page.getResult();
   
        return page;
    }

    @Override
    public Result findAllMaps() {
//        adds.push(new BMap.Point(data.gridnMaps[x].lng,data.gridnMaps[x].lat));
//					addNames.push(data.nameMaps[x].addressName);
        Map map=new HashMap();
        Page page=addressDao.findMaps("");
        List<Map<String,String>>  gridnMaps=new ArrayList<>();
        List<Map<String,String>> nameMaps=new ArrayList<>();
        List<Address> addresses = page.getResult();
        Map addressName=new HashMap();
        Map x=new HashMap();

        for (Address address : addresses) {
            String addressName1 = address.getAddress();
            String lng1 = address.getLng();
            String lat1 = address.getLat();
            addressName.put("addressName",addressName1);
           nameMaps.add(addressName);
            x.put("lng",lat1);
            x.put("lat",lat1);
            gridnMaps.add(x);
        }
        map.put("gridnMaps",gridnMaps);
        map.put("nameMaps",nameMaps);


        return new Result(true,"success",map);
    }
}
