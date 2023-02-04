package kaptcha.config;

import kaptcha.filter.LoginKaptchaFilter;
import kaptcha.handler.CustomAuthenticationFailureHandler;
import kaptcha.handler.CustomAuthenticationSuccessHandler;
import kaptcha.handler.CustomLogoutSuccessHandler;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

/**
 * security 配置
 *
 * @author Oliver
 * @date 2023年01月06日 15:24
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
    public LoginKaptchaFilter loginKaptchaFilter() throws Exception {
        LoginKaptchaFilter loginKaptchaFilter = new LoginKaptchaFilter();
        loginKaptchaFilter.setAuthenticationManager(authenticationManagerBean());
        loginKaptchaFilter.setAuthenticationSuccessHandler(
            new CustomAuthenticationSuccessHandler());
        loginKaptchaFilter.setAuthenticationFailureHandler(
            new CustomAuthenticationFailureHandler());
        return loginKaptchaFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .mvcMatchers("/get-kaptcha").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
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
        http.addFilterAt(loginKaptchaFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 自定义了UserDetailsService需要暴露authenticationManagerBean，否则AuthenticationManager就会是null
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
