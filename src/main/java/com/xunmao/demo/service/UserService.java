package com.xunmao.demo.service;

import java.util.List;

import com.xunmao.demo.pojo.User;

public interface UserService {

    public List<User> listUsers();

    public User findUserById(int userId);
}
