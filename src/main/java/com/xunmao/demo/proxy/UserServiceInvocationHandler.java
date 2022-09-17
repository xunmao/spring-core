package com.xunmao.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.xunmao.demo.service.UserService;
import com.xunmao.demo.util.Logger;

public class UserServiceInvocationHandler implements InvocationHandler {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logBefore(method.getName());
        Object result = method.invoke(userService, args);
        logAfter(method.getName());
        return result;
    }

    private void logBefore(String methodName) {
        Logger.logBefore(this.getClass().getName(), methodName);
    }

    private void logAfter(String methodName) {
        Logger.logAfter(this.getClass().getName(), methodName);
    }
}
