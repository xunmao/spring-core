package com.xunmao.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xunmao.demo.service.UserService;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.listUsers().forEach(System.out::println);
    }
}
