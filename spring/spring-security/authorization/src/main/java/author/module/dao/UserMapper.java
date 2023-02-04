package author.module.dao;

import java.util.List;

import author.module.entity.Role;
import author.module.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Oliver
 * @date 2023年01月06日 12:26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<Role> getUserRole(@Param("uid") Long uid);

    User getUser(@Param("username") String username);
}
