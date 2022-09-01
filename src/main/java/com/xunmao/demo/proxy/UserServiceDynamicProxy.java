package com.xunmao.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.xunmao.demo.service.UserService;

public class UserServiceDynamicProxy implements InvocationHandler {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Proxy.html
    public Object getInstance() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                this.userService.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logBefore(method.getName());
        Object result = method.invoke(userService, args);
        logAfter(method.getName());
        return result;
    }

    private void logBefore(String methodName) {
        System.out.println("this.getClass().getClassLoader(): " + this.getClass().getClassLoader());
        System.out.println("this.getClass(): " + this.getClass());
        System.out.println("userService.getClass(): " + this.userService.getClass());
        System.out.println("UserService.class: " + UserService.class);
        System.out.println("this: " + this);
        System.out.println("即将调用" + methodName + "方法");
    }

    private void logAfter(String methodName) {
        System.out.println("完成调用" + methodName + "方法");
    }

}
