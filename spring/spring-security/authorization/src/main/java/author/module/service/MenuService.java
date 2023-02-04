package author.module.service;


import java.util.List;

import author.module.dao.MenuMapper;
import author.module.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Oliver
 * @date 2023年01月06日 12:53
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MenuService {

    private final MenuMapper menuMapper;

    public List<Menu> getMenus() {
        return menuMapper.getMenus();
    }

}
