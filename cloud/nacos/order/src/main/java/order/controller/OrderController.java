package order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Oliver
 * @date 2023年02月11日 17:39
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

    @Value("${user.name}")
    private String name;

    @GetMapping("reduct")
    public String orderReduct() {
        return restTemplate.getForObject("http://stock-service/stock/reduct", String.class);
    }

    @GetMapping("get")
    public String get() {
        return name;
    }
}
