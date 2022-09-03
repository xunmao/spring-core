package com.xunmao.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogAspectByAnnotation {

    @Pointcut("execution(* com.xunmao.demo.service.impl.UserServiceImpl.*(..))")
    private void logAspectByAnnotationPointCut() {
    }

    // 如果 logAspectByAnnotationPointCut 方法与通知 Advice 定义在不同的类中，需要在方法之前补充类的全限定名（如下）
    // @Before("com.xunmao.demo.aspect.LogAspectByAnnotation.logAspectByAnnotationPointCut()")
    // 如果 logAspectByAnnotationPointCut 方法与通知 Advice 定义在同一个类中，可以省略类的全限定名
    @Before("logAspectByAnnotationPointCut()")
    public void before(JoinPoint joinPoint) {
        System.out.println("即将调用" + joinPoint.toShortString() + "方法");
    }

    @AfterReturning("logAspectByAnnotationPointCut()")
    public void afterReturning(JoinPoint joinPoint) {
        System.out.println("完成调用" + joinPoint.toShortString() + "方法");
    }
}
