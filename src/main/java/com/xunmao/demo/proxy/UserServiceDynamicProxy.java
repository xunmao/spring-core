package com.xunmao.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.xunmao.demo.service.UserService;
import com.xunmao.demo.util.Logger;

public class UserServiceDynamicProxy implements InvocationHandler {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Object getInstance() {
        // 1. 获取真实角色的类加载器
        ClassLoader loader = userService.getClass().getClassLoader();
        System.out.println(loader); // sun.misc.Launcher$AppClassLoader@232204a1

        // 2. 获取真实角色上所有的方法
        Class<?>[] interfaces = userService.getClass().getInterfaces();
        System.out.println(interfaces); // [Ljava.lang.Class;@3d921e20

        // 3. 获取 InvocationHandler 接口实现类的实例（相当于这个类的实例，可以用 this 代替）
        // 4. 生成动态代理类的实例，并将其返回
        return Proxy.newProxyInstance(loader, interfaces, this);
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
