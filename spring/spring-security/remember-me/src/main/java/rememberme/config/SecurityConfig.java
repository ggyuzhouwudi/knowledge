package rememberme.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import rememberme.filter.LoginRememberMeFilter;
import rememberme.handler.CustomAuthenticationFailureHandler;
import rememberme.handler.CustomAuthenticationSuccessHandler;
import rememberme.handler.CustomLogoutSuccessHandler;
import rememberme.service.CustomPersistentTokenBasedRememberMeServices;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * security 配置
 *
 * @author Oliver
 * @date 2023年01月06日 15:24
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    //⾃定义内存数据源
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(
            User.withUsername("root").password("{noop}123").roles("admin").build());
        return inMemoryUserDetailsManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public LoginRememberMeFilter loginRememberMeFilter() throws Exception {
        LoginRememberMeFilter loginKaptchaFilter = new LoginRememberMeFilter();
        loginKaptchaFilter.setAuthenticationManager(authenticationManagerBean());
        loginKaptchaFilter.setRememberMeServices(rememberMeServices());
        loginKaptchaFilter.setAuthenticationSuccessHandler(
            new CustomAuthenticationSuccessHandler());
        loginKaptchaFilter.setAuthenticationFailureHandler(
            new CustomAuthenticationFailureHandler());
        return loginKaptchaFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .mvcMatchers("/get-kaptcha").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .and()
            //开启记住我功能 cookie 进⾏实现 1.认证成功保存记住我 cookie 到客户端 2.只有 cookie 写⼊客户端成功才能实现⾃动登录功能
            .rememberMe().rememberMeServices(rememberMeServices())
            .and()
            .logout()
            // 设置注销路径
            .logoutRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/logout",
                HttpMethod.DELETE.name())))
            // 注销成功后处理
            .logoutSuccessHandler(new CustomLogoutSuccessHandler())
            /*.and()
            .exceptionHandling()
            // 认证异常处理
            .authenticationEntryPoint((request, response, authException) -> {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("尚未认证，请进⾏认证操作");
            })
            // 授权异常处理
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("⽆权访问!");
            })*/
            .and()
            .csrf().disable();
        // 替换验证filter
        http.addFilterAt(loginRememberMeFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 自定义了UserDetailsService需要暴露authenticationManagerBean，否则AuthenticationManager就会是null
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // token记录在内存
    /*@Bean
    public RememberMeServices rememberMeServices() {
        return new CustomPersistentTokenBasedRememberMeServices(UUID.randomUUID().toString(),
            userDetailsService(), new InMemoryTokenRepositoryImpl());
    }*/

    // token记录在数据库内
    @Bean
    public RememberMeServices rememberMeServices() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return new CustomPersistentTokenBasedRememberMeServices(UUID.randomUUID().toString(),
            userDetailsService(), tokenRepository);
    }

}
