package rememberme.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.util.ObjectUtils;
import rememberme.exception.KaptchaNotMatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Oliver
 * @date 2023年01月06日 17:35
 */
public class LoginRememberMeFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            try {
                Map<String, String> map = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                String username = map.get(getUsernameParameter());
                username = (username != null) ? username.trim() : "";
                String password = map.get(getPasswordParameter());
                password = (password != null) ? password.trim() : "";

                String rememberValue = map.get(AbstractRememberMeServices.DEFAULT_PARAMETER);
                // 因为已经将流读出了，所以这里要记录到请求内用于下游
                if (!ObjectUtils.isEmpty(rememberValue)) {
                    request.setAttribute(AbstractRememberMeServices.DEFAULT_PARAMETER, rememberValue);
                }
                System.out.println("⽤户名: " + username + " 密码: " + password + " 是否记住我: " + rememberValue);
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                // Allow subclasses to set the "details" property
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new KaptchaNotMatchException("Content-Type应为application/json");
    }

}
