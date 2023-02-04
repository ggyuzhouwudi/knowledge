package custom.auth.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Oliver
 * @date 2023年01月06日 17:06
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 数据库根据用户名查询出用户实体，这里模拟一下
        // 实际一般会实现UserDetails，定义自己的业务用户模型

        /*if (Objects.isNull(user)) {
            throw new RuntimeException("⽤户不存在");
        }*/
        return User.builder().username("root").password("{noop}123456").roles("admin")
            .build();
    }
}
