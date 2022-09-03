package com.xunmao.demo.aspect;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xunmao.demo.service.UserService;

public class UserServiceLogAspectTest {

    @Test
    public void shouldListUsersWithLogAspect() {
        ApplicationContext context = new ClassPathXmlApplicationContext("daos.xml", "services.xml");

        UserService userService = context.getBean("userServiceImpl", com.xunmao.demo.service.UserService.class);

        userService.listUsers().forEach(System.out::println);
    }
}
