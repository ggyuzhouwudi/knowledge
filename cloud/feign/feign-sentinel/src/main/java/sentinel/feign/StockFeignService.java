package sentinel.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * name 服务名，Path算是公共部分地址
 *
 * @author Oliver
 * @date 2023年02月12日 0:14
 */
@FeignClient(name = "stock-service", path = "/stock")
public interface StockFeignService {

    //声明需要调用的Rest接口，一模一样就行，会动态代理生成实现类
    // 注意在feign接口中如果使用了@PathVariable，必须填入参数名，如：@PathVariable("id")
    @GetMapping("reduct")
    String reduct();
}
