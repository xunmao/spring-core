package com.xunmao.demo.aspect;

import org.aspectj.lang.JoinPoint;

import com.xunmao.demo.util.Logger;

public class LogAspectByCustomized {

    // 通过自定义方法实现通知（Advice）时，可以加上 JoinPoint 类型的参数，来获取代理对象的方法名等
    // https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-advice-params-the-joinpoint
    public void before(JoinPoint joinPoint) {
        Logger.logBefore(this.getClass().getName(), joinPoint.toShortString());
        // 输出：即将调用execution(UserService.listUsers())方法
    }

    public void afterReturning(JoinPoint joinPoint) {
        Logger.logAfter(this.getClass().getName(), joinPoint.toShortString());
        // 输出：完成调用execution(UserService.listUsers())方法
    }
}
