package com.xunmao.demo.proxy;

import java.lang.reflect.Proxy;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xunmao.demo.pojo.User;
import com.xunmao.demo.service.UserService;

public class UserServiceDynamicProxyTest {

    @Test
    public void shouldListUsers() {
        // 1. 生成 ApplicationContext 类的实例，以便获取 Spring 容器中的其他实例
        ApplicationContext context = new ClassPathXmlApplicationContext("daos.xml", "services.xml");

        // 2. 获取 UserServiceInvocationHandler 类的实例
        UserServiceInvocationHandler userServiceInvocationHandler = context.getBean("userServiceInvocationHandler",
                UserServiceInvocationHandler.class);

        // 3. 获取真实角色的类加载器
        ClassLoader loader = userServiceInvocationHandler.getUserService().getClass().getClassLoader();
        System.out.println(loader); // sun.misc.Launcher$AppClassLoader@232204a1

        // 4. 获取真实角色上所有的方法
        Class<?>[] interfaces = userServiceInvocationHandler.getUserService().getClass().getInterfaces();
        System.out.println(interfaces); // [Ljava.lang.Class;@3d921e20

        // 5. 生成动态代理类的实例
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(loader, interfaces,
                userServiceInvocationHandler);

        // 6. 消费动态代理类的实例
        userServiceProxy.listUsers().forEach(System.out::println);
    }

    @Test
    public void shouldFindUserById() {
        // 1. 生成 ApplicationContext 类的实例，以便获取 Spring 容器中的其他实例
        ApplicationContext context = new ClassPathXmlApplicationContext("daos.xml", "services.xml");

        // 2. 通过 Spring 获取 UserServiceDynamicProxy 类的实例
        UserServiceDynamicProxy userServiceDynamicProxy = context.getBean("userServiceDynamicProxy",
                UserServiceDynamicProxy.class);

        // 3. 生成动态代理类的实例
        UserService userService = (UserService) userServiceDynamicProxy.getInstance();

        // 4. 消费动态代理类的实例
        User user = userService.findUserById(1);
        System.out.println(user);
    }
}
