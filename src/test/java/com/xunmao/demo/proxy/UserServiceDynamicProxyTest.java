package com.xunmao.demo.proxy;

import java.lang.reflect.Proxy;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xunmao.demo.pojo.User;
import com.xunmao.demo.service.UserService;

public class UserServiceDynamicProxyTest {

    @Test
    public void shouldFindUserById() {
        ApplicationContext context = new ClassPathXmlApplicationContext("daos.xml", "services.xml");
        UserServiceDynamicProxy userServiceDynamicProxy = context.getBean("userServiceDynamicProxy",
                UserServiceDynamicProxy.class);

        UserService userService = (UserService) userServiceDynamicProxy.getInstance();

        User user = userService.findUserById(1);
        System.out.println(user);
    }

    @Test
    public void shouldListUsers() {
        ApplicationContext context = new ClassPathXmlApplicationContext("daos.xml", "services.xml");
        UserService userService = context.getBean("userServiceImpl", UserService.class);

        UserServiceInvocationHandler userServiceInvocationHandler = new UserServiceInvocationHandler();
        userServiceInvocationHandler.setUserService((UserService) userService);

        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(
                UserServiceInvocationHandler.class.getClassLoader(),
                userService.getClass().getInterfaces(),
                userServiceInvocationHandler);

        userServiceProxy.listUsers().forEach(System.out::println);
    }
}
