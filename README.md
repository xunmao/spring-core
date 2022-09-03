# springcore
此项目用于学习和尝试 Spring 框架的各种功能。

## 动态代理

### JDK动态代理

#### 第一次实现

JDK动态代理主要涉及两个类：`java.lang.reflect.Proxy` 和 `java.lang.reflect.InvocationHandler`。  
首先，实现 InvocationHandler。

```java
public class UserServiceInvocationHandler implements InvocationHandler {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logBefore(method.getName());
        Object result = method.invoke(userService, args);
        logAfter(method.getName());
        return result;
    }

    private void logBefore(String methodName) {
        System.out.println("即将调用" + methodName + "方法");
    }

    private void logAfter(String methodName) {
        System.out.println("完成调用" + methodName + "方法");
    }
}
```

然后，通过 Proxy 类的静态方法来生成动态代理的实例。

```java
public class UserServiceDynamicProxyTest {

    @Test
    public void shouldListUsers() {
        // 1. 生成 ApplicationContext 类的实例，以便获取 Spring 容器中的其他实例
        ApplicationContext context = new ClassPathXmlApplicationContext("daos.xml", "services.xml");

        // 2. 获取 UserServiceInvocationHandler 类的实例
        UserServiceInvocationHandler userServiceInvocationHandler = context.getBean("userServiceInvocationHandler", UserServiceInvocationHandler.class);

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
}
```

#### 第二次实现

简化客户端调用处理，将“生成动态代理类”封装为代理类的方法。

```java
public class UserServiceDynamicProxy implements InvocationHandler {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Object getInstance() {
        // 1. 获取真实角色的类加载器
        ClassLoader loader = this.userService.getClass().getClassLoader();
        System.out.println(loader); // sun.misc.Launcher$AppClassLoader@232204a1

        // 2. 获取真实角色上所有的方法
        Class<?>[] interfaces = this.userService.getClass().getInterfaces();
        System.out.println(interfaces); // [Ljava.lang.Class;@3d921e20

        // 3. 获取 InvocationHandler 接口实现类的实例（相当于这个类的实例，可以用 this 代替）
        // 4. 生成动态代理类的实例，并将其返回
        return Proxy.newProxyInstance(loader, interfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logBefore(method.getName());
        Object result = method.invoke(userService, args);
        logAfter(method.getName());
        return result;
    }

    private void logBefore(String methodName) {
        System.out.println("即将调用" + methodName + "方法");
    }

    private void logAfter(String methodName) {
        System.out.println("完成调用" + methodName + "方法");
    }
}
```

客户端的调用处理将被简化。

```java
public class UserServiceDynamicProxyTest {

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
```

#### 第三次实现

将动态代理类与 `UserService` 接口解耦，抽象为通用的类。

```java
public class LogDynamicProxy implements InvocationHandler {

    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object getInstance() {
        ClassLoader loader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(loader, interfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logBefore(method.getName());
        Object result = method.invoke(target, args);
        logAfter(method.getName());
        return result;
    }

    private void logBefore(String methodName) {
        System.out.println("即将调用" + methodName + "方法");
    }

    private void logAfter(String methodName) {
        System.out.println("完成调用" + methodName + "方法");
    }
}
```

客户端调用。

```java
public class UserServiceDynamicProxyTest {

    @Test
    public void shouldFindUserByIdWithLogDynamicProxy() {
        // 1. 生成 ApplicationContext 类的实例，以便获取 Spring 容器中的其他实例
        ApplicationContext context = new ClassPathXmlApplicationContext("daos.xml", "services.xml");

        // 2. 通过 Spring 获取 LogDynamicProxy 类的实例
        LogDynamicProxy logDynamicProxy = context.getBean("logDynamicProxy", LogDynamicProxy.class);

        // 3. 生成动态代理类的实例
        UserService userService = (UserService) logDynamicProxy.getInstance();

        // 4. 消费动态代理类的实例
        User user = userService.findUserById(1);
        System.out.println(user);
    }
}
```