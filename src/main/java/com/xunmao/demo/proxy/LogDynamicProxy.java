package com.xunmao.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.xunmao.demo.util.Logger;

public class LogDynamicProxy implements InvocationHandler {

    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object getInstance() {
        ClassLoader loader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(loader, interfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logBefore(method.getName());
        Object result = method.invoke(target, args);
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
