package author.metasource;

import java.util.Collection;
import java.util.List;

import author.module.entity.Menu;
import author.module.entity.Role;
import author.module.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

/**
 * 权限的配置会被封装成FilterInvocationSecurityMetadataSource， 自定义自己的SecurityMetadataSource
 *
 * @author Oliver
 * @date 2023年01月06日 13:04
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    //用于路径对比
    private final MenuService menuService;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 自定义动态资源权限元数据信息
     *
     * @param object 当前请求对象
     * @return java.util.Collection<org.springframework.security.access.ConfigAttribute>
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
        throws IllegalArgumentException {
        // 当前uri
        String requestURI = ((FilterInvocation) object).getRequest().getRequestURI();
        List<Menu> menus = menuService.getMenus();
        // 构造当前的uri需要的角色信息
        for (Menu menu : menus) {
            if (antPathMatcher.match(menu.getPattern(), requestURI)) {
                String[] roles = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(roles);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
