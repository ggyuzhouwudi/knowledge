package order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Oliver
 * @date 2023年02月18日 20:44
 */
@FeignClient(value = "stock-seata-service", path = "/stock")
public interface StockService {

    @GetMapping("reduct")
    Object reduct(@RequestParam("productId") Long productId);
}
