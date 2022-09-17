package com.xunmao.demo.aspect;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import com.xunmao.demo.util.Logger;

public class LogAspectBySpringApi implements MethodBeforeAdvice, AfterReturningAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        Logger.logBefore(this.getClass().getName(), method.getName());
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        Logger.logAfter(this.getClass().getName(), method.getName());
    }
}
