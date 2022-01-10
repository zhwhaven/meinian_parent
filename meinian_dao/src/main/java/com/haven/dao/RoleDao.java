package com.haven.dao;

import com.haven.pojo.Role;

import java.util.HashSet;
import java.util.Set;

public interface RoleDao {
   public Set<Role> selectRoleByUserID(Integer id);
}
