package com.xunmao.demo.proxy;

import java.util.List;

import com.xunmao.demo.pojo.User;
import com.xunmao.demo.service.UserService;

public class UserServiceStaticProxy implements UserService {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User findUserById(int userId) {
        logBefore("findUserById");
        User user = userService.findUserById(userId);
        logAfter("findUserById");
        return user;
    }

    @Override
    public List<User> listUsers() {
        logBefore("listUsers");
        List<User> users = userService.listUsers();
        logAfter("listUsers");
        return users;
    }

    private void logBefore(String methodName) {
        System.out.println("即将调用" + methodName + "方法");
    }

    private void logAfter(String methodName) {
        System.out.println("完成调用" + methodName + "方法");
    }
}
