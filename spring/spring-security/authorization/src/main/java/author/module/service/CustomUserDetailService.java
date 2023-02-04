package author.module.service;

import java.util.Objects;

import author.module.dao.UserMapper;
import author.module.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定义通过账号获取用户信息
 * @author Oliver
 * @date 2023年01月06日 12:49
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserDetailService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUser(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("⽤户不存在");
        }
        user.setRoles(userMapper.getUserRole(user.getId()));
        return user;
    }
}
