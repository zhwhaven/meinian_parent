package com.haven.dao;

import com.haven.pojo.Permission;

import java.util.Set;

public interface PermissionDao {
    Set<Permission> selectPermissionByRoleid(Integer id);
}
