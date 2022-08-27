package com.xunmao.demo.service;

import java.util.List;

import com.xunmao.demo.dao.UserDao;
import com.xunmao.demo.pojo.User;

public class UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> listUsers() {
        return userDao.listUsers();
    }

    public User findUserById(int userId) {
        return userDao.findUserById(userId);
    }
}
