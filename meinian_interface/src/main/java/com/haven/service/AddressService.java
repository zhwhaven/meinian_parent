package com.haven.service;

import com.github.pagehelper.Page;
import com.haven.entry.QueryPageBean;
import com.haven.entry.Result;

import java.util.Map;

public interface AddressService {
    void addAddress(Map param);

    Page findPage(QueryPageBean queryPageBean);

    Result findAllMaps();
}
