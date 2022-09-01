package com.xunmao.demo.service.impl;

import java.util.List;

import com.xunmao.demo.dao.UserDao;
import com.xunmao.demo.pojo.User;
import com.xunmao.demo.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    public User findUserById(int userId) {
        return userDao.findUserById(userId);
    }
}
