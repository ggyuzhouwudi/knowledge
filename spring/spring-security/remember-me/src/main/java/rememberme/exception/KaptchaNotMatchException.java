package rememberme.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @author Oliver
 * @date 2023年01月06日 17:34
 */
public class KaptchaNotMatchException extends AuthenticationException {

    public KaptchaNotMatchException(String msg) {
        super(msg);
    }

    public KaptchaNotMatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
