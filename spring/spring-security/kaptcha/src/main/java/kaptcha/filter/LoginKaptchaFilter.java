package kaptcha.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import kaptcha.exception.KaptchaNotMatchException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Oliver
 * @date 2023年01月06日 17:35
 */
public class LoginKaptchaFilter extends UsernamePasswordAuthenticationFilter {

    public static final String FORM_KAPTCHA_KEY = "kaptcha";
    private String kaptchaParameter = FORM_KAPTCHA_KEY;

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
                String kaptcha = map.get(getKaptchaParameter());
                kaptcha = (kaptcha != null) ? kaptcha.trim() : "";
                String sessionKaptcha = (String) request.getSession()
                        .getAttribute(getKaptchaParameter());
                if (StringUtils.hasLength(kaptcha) && StringUtils.hasLength(sessionKaptcha)
                        && kaptcha.equalsIgnoreCase(sessionKaptcha)) {
                    String username = map.get(getUsernameParameter());
                    username = (username != null) ? username.trim() : "";
                    String password = map.get(getPasswordParameter());
                    password = (password != null) ? password.trim() : "";
                    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                    // Allow subclasses to set the "details" property
                    setDetails(request, authRequest);
                    return this.getAuthenticationManager().authenticate(authRequest);
                }
                throw new KaptchaNotMatchException("验证码不匹配");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new KaptchaNotMatchException("Content-Type应为application/json");
    }

    public String getKaptchaParameter() {
        return kaptchaParameter;
    }

    public void setKaptchaParameter(String kaptchaParameter) {
        this.kaptchaParameter = kaptchaParameter;
    }
}
