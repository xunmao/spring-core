package com.xunmao.demo.proxy;

import java.lang.reflect.Proxy;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xunmao.demo.pojo.User;
import com.xunmao.demo.service.UserService;
import com.xunmao.demo.service.impl.UserServiceImpl;

public class UserServiceDynamicProxyTest {

    @Test
    public void shouldListUsers() {
        // 1. 生成 ApplicationContext 类的实例，以便获取 Spring 容器中的其他实例
        ApplicationContext context = new ClassPathXmlApplicationContext("daos.xml", "services.xml");

        // 2. 获取 UserServiceImpl 类的实例（真实角色）
        UserServiceImpl userServiceImpl = context.getBean("userServiceImpl", UserServiceImpl.class);

        // 3. 获取 UserServiceInvocationHandler 类的实例
        UserServiceInvocationHandler userServiceInvocationHandler = new UserServiceInvocationHandler();

        // 4. 配置 UserServiceInvocationHandler 类的实例（将真实角色“放入”代理类）
        userServiceInvocationHandler.setUserService((UserService) userServiceImpl);

        // 5. 获取真实角色的类加载器
        ClassLoader loader = userServiceImpl.getClass().getClassLoader();
        System.out.println(loader); // sun.misc.Launcher$AppClassLoader@232204a1

        // 6. 获取真实角色上所有的方法
        Class<?>[] interfaces = userServiceImpl.getClass().getInterfaces();
        System.out.println(interfaces); // [Ljava.lang.Class;@3d921e20

        // 7. 生成动态代理类的实例
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(loader, interfaces,
                userServiceInvocationHandler);

        // 8. 消费动态代理类的实例
        userServiceProxy.listUsers().forEach(System.out::println);
    }

    @Test
    public void shouldFindUserById() {
        // 1. 生成 ApplicationContext 类的实例，以便获取 Spring 容器中的其他实例
        ApplicationContext context = new ClassPathXmlApplicationContext("daos.xml", "services.xml");

        // 2. 通过 Spring 获取 UserServiceDynamicProxy 类的实例
        // 3. 通过 Spring 配置 UserServiceDynamicProxy 类的实例（将真实角色“放入”代理类）
        UserServiceDynamicProxy userServiceDynamicProxy = context.getBean("userServiceDynamicProxy",
                UserServiceDynamicProxy.class);

        // 4. 生成动态代理类的实例
        UserService userService = (UserService) userServiceDynamicProxy.getInstance();

        // 5. 消费动态代理类的实例
        User user = userService.findUserById(1);
        System.out.println(user);
    }
}
