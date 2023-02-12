package stock.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Oliver
 * @date 2023年02月11日 21:19
 */
@RestController
@RequestMapping("stock")
public class StockController {

    @Value("${server.port}")
    private Integer serverPort;

    @GetMapping("reduct")
    public String reduct() {
        return "reduct-" + serverPort;
    }
}
