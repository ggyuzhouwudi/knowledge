package custom.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Oliver
 * @date 2023年01月06日 14:37
 */
@RestController
public class TestController {

    @GetMapping("/user")
    public String getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return "账号:" + user.getUsername() + "密码:" + user.getPassword() + "凭证:"
            + authentication.getCredentials() + "权限:" + authentication.getAuthorities();
    }

    @GetMapping("/user-multithreading")
    public String getUserMultithreading() {
        /*
        1. MODE THREADLOCAL：这种存放策略是将 SecurityContext 存放在 ThreadLocal
            中 Threadlocal 的特点是在哪个线程中存储就要在哪个线程中读取，这其
            实⾮常适合 web 应⽤，因为在默认情况下，⼀个请求⽆论经过多少 Filter 到达
            Servlet，都是由⼀个线程来处理的。这也是 SecurityContextHolder 的默认存储
            策略，这种存储策略意味着如果在具体的业务处理代码中，开启了⼦线程，在⼦线程中
            去获取登录⽤户数据，就会获取不到。
            2. MODE INHERITABLETHREADLOCAL：这种存储模式适⽤于多线程环境，如果希望在⼦
            线程中也能够获取到登录⽤户数据，那么可以使⽤这种存储模式。
            3. MODE GLOBAL：这种存储模式实际上是将数据保存在⼀个静态变量中，在 JavaWeb开
            发中，这种模式很少使⽤到。
        */
        // 默认策略无法多线程获取，会报错；需要修改为MODE INHERITABLETHREADLOCAL
        new Thread(() -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            System.out.println("账号:" + user.getUsername() + "密码:" + user.getPassword() + "凭证:"
                + authentication.getCredentials() + "权限:" + authentication.getAuthorities());
        }).start();
        return "success";
    }
}
