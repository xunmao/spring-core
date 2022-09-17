package com.xunmao.demo.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceTest {

    @Test
    public void should() {
        ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
        UserService userService = context.getBean("userServiceImpl", UserService.class);
        userService.listUsers().forEach(System.out::println);
    }
}
