package com.xunmao.demo.dao.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.xunmao.demo.dao.UserDao;
import com.xunmao.demo.pojo.User;

public class UserDaoOracleImpl implements UserDao {

    @Override
    public List<User> listUsers() {
        System.out.println("使用 Oracle 获取了用户列表");

        Date lastUpdate = new Date();
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Firstname-01", "Lastname-01", lastUpdate));
        users.add(new User(2, "Firstname-02", "Lastname-02", lastUpdate));
        users.add(new User(3, "Firstname-03", "Lastname-03", lastUpdate));

        return users;
    };

    @Override
    public User findUserById(int userId) {
        System.out.println("使用 Oracle 获取了一个用户");

        Date lastUpdate = new Date();
        User user = new User(1, "Firstname-01", "Lastname-01", lastUpdate);
        return user;
    };
}
