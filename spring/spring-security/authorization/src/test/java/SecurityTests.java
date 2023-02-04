import javax.annotation.Resource;

import author.AuthorizationApplication;
import author.module.dao.UserMapper;
import author.module.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Oliver
 * @date 2023年01月05日 2:20
 */
@SpringBootTest(classes = AuthorizationApplication.class)
public class SecurityTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void data() {
        User admin = userMapper.getUser("admin");
        System.out.println("admin = " + admin);
    }

}
