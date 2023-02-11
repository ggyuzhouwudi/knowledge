package order.controller;

import order.feign.StockFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Oliver
 * @date 2023年02月11日 21:21
 */
@RestController
@RequestMapping("order")
public class OrderController {

    /*@Resource
    private RestTemplate restTemplate;*/
    @Resource
    private StockFeignService service;

    /*@GetMapping("add")
    public String add() {
        return restTemplate.getForObject("http://stock-ribbon-service/stock/reduct", String.class);
    }*/

    @GetMapping("add")
    public String add() {
        return service.reduct();
    }
}
