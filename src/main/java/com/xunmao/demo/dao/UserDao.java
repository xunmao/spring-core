package com.xunmao.demo.dao;

import java.util.List;

import com.xunmao.demo.pojo.User;

public interface UserDao {

    public List<User> listUsers();

    public User findUserById(int userId);
}
