package com.xunmao.demo.service;

import java.util.List;

import com.xunmao.demo.dao.UserDao;
import com.xunmao.demo.pojo.User;

public interface UserService {

    public void setUserDao(UserDao userDao);

    public List<User> listUsers();

    public User findUserById(int userId);
}
