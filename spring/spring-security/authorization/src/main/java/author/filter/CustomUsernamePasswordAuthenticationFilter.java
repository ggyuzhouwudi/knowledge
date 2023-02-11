package author.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Oliver
 * @date 2022年12月29日 17:52
 */
public class CustomUsernamePasswordAuthenticationFilter extends
        UsernamePasswordAuthenticationFilter {

    public static final String KAPTCHA_FORM_USERNAME_KEY = "kaptcha";

    private String kaptchaParameter = KAPTCHA_FORM_USERNAME_KEY;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            try {
                Map<String, String> map = new ObjectMapper().readValue(request.getInputStream(),
                        Map.class);
                String username = map.get(getUsernameParameter());
                username = (username != null) ? username.trim() : "";
                String password = map.get(getPasswordParameter());
                password = (password != null) ? password.trim() : "";
                //因为这里已经将流信息读取到了，之后拦截不到。这里获取到前端传过来的remember-me参数并将其设置进request里
                String rememberMe = map.get(
                        AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
                rememberMe = (rememberMe != null) ? rememberMe.trim() : "";
                if (StringUtils.hasLength(rememberMe)) {
                    request.setAttribute(
                            AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY,
                            rememberMe);
                }
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                // Allow subclasses to set the "details" property
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new AuthenticationServiceException("media-type必须是application/json");
    }

    public String getKaptchaParameter() {
        return kaptchaParameter;
    }

    public void setKaptchaParameter(String kaptchaParameter) {
        this.kaptchaParameter = kaptchaParameter;
    }
}
