package com.xunmao.demo.proxy;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xunmao.demo.pojo.User;

public class UserServiceStaticProxyTest {

    @Test
    public void shouldFindUserById() {
        ApplicationContext context = new ClassPathXmlApplicationContext("daos.xml", "services.xml");
        UserServiceStaticProxy userServiceStaticProxy = context.getBean("userServiceStaticProxy",
                UserServiceStaticProxy.class);

        User user = userServiceStaticProxy.findUserById(1);
        System.out.println(user);

        userServiceStaticProxy.listUsers().forEach(System.out::println);
    }
}
