package management.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * security 配置
 *
 * @author Oliver
 * @date 2023年01月06日 15:24
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final FindByIndexNameSessionRepository sessionRepository;
    //⾃定义内存数据源
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(
            User.withUsername("root").password("{noop}123").roles("admin").build());
        return inMemoryUserDetailsManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .and()
            .logout()
            // 设置注销路径
            .logoutRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/logout",
                HttpMethod.DELETE.name())))
            .and()
            .csrf().disable()
            .sessionManagement()// 开启会话管理
            .maximumSessions(1)// 用户只能开启一个session
            .expiredSessionStrategy(event -> {
                HttpServletResponse response = event.getResponse();
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> result = new HashMap<>();
                result.put("status", 500);
                result.put("msg", "当前会话已经失效,请重新登录!");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println(new ObjectMapper().writeValueAsString(result));
                response.flushBuffer();
            })
            .maxSessionsPreventsLogin(true);//登录之后禁止再次登录
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }

}
