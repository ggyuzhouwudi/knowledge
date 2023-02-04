package custom.auth.config;

import custom.auth.handler.CustomAuthenticationFailureHandler;
import custom.auth.handler.CustomAuthenticationSuccessHandler;
import custom.auth.handler.CustomLogoutSuccessHandler;
import custom.auth.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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

    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.customUserDetailService);
    }

    /*# 说明
            - permitAll() 代表放⾏该资源,该资源为公共资源 ⽆需认证和授权可以直接访问
            - anyRequest().authenticated() 代表所有请求,必须认证之后才能访问
            - formLogin() 代表开启表单认证
            !" 注意: 放⾏资源必须放在所有认证请求之前!
        */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            // 认证成功后处理
            .successHandler(new CustomAuthenticationSuccessHandler())
            // 认证失败后处理
            .failureHandler(new CustomAuthenticationFailureHandler())
            .and()
            .logout()
            // 设置注销路径
            .logoutRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/logout",
                HttpMethod.DELETE.name())))
            // 注销成功后处理
            .logoutSuccessHandler(new CustomLogoutSuccessHandler())
            .and()
            .csrf().disable();
    }


}
