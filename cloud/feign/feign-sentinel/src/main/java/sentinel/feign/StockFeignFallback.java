package sentinel.feign;

import org.springframework.stereotype.Component;

/**
 * @author Oliver
 * @date 2023年02月17日 15:28
 */
@Component
public class StockFeignFallback implements StockFeignService{
    @Override
    public String reduct() {
        return "";
    }

    @Override
    public String reductSentinel() {
        return "降级啦！！！";
    }
}
