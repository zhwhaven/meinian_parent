package com.haven.dao;

import com.haven.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    User selectByUsername(@Param("username") String username);
}
