package com.haven.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface AddressDao {
    void addAddress(Map param);

    Page findMaps(@Param("query") String query);
}
