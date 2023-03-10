package sentinel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sentinel.feign.StockFeignService;

import javax.annotation.Resource;

/**
 * @author Oliver
 * @date 2023年02月11日 21:21
 */
@RestController
@RequestMapping("order")
public class OrderController {
    @Resource
    private StockFeignService service;

    @GetMapping("add")
    public String add() {
        return service.reductSentinel();
    }
}
