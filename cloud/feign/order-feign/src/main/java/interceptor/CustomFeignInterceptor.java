package interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Oliver
 * @date 2023年02月12日 12:12
 */
@Slf4j
public class CustomFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("xxx","xxx");
        requestTemplate.query("a","a");
        log.info("我是拦截器……");
        //requestTemplate.uri("/abc");
    }
}
