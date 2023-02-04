package encode.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * security 配置
 *
 * @author Oliver
 * @date 2023年01月06日 15:24
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //推荐使用默认的DelegatingPasswordEncoder，根据前缀来自动选择加密方式，更灵活，能自动升级
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(
            User.withUsername("root")
                .password("{bcrypt}$2a$10$WGFkRsZC0kzafTKOPcWONeLvNvg2jqd3U09qd5gjJGSHE5b0yoy6a")
                /*.password("{pbkdf2}ad96bcdcac55bbd134429e867951ed5aa576333921a60799875b5859e34af647015e7102eead77d1")*/
                .roles("admin").build());
        return inMemoryUserDetailsManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123"));
        //!"2.Pbkdf2PasswordEncoder
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();
        System.out.println(pbkdf2PasswordEncoder.encode("123"));
        //!"3.SCryptPasswordEncoder !"需要额外引⼊依赖
        SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder();
        System.out.println(sCryptPasswordEncoder.encode("123"));
        //!"4.Argon2PasswordEncoder !"需要额外引⼊依赖
        Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder();
        System.out.println(argon2PasswordEncoder.encode("123"));
    }

}
