package com.haven.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.haven.dao.UserDao;
import com.haven.pojo.User;
import com.haven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public User selectByUsername(String username) {
        User user = userDao.selectByUsername(username);
        return user;
    }
}
