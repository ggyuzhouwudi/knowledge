package rememberme.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 自定义注销处理
 * @author Oliver
 * @date 2023年01月06日 16:32
 */
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("msg", "注销成功");
        result.put("status", 200);
        response.setContentType("application/json;charset=UTF8");
        response.getWriter().println(new ObjectMapper().writeValueAsString(result));
    }
}
