package com.haven.service;

import com.haven.pojo.User;

public interface UserService {
    User selectByUsername(String username);
}
