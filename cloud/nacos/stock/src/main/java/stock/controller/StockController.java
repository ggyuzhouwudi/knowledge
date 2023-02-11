package stock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Oliver
 * @date 2023年02月11日 17:37
 */
@RestController
@RequestMapping("stock")
public class StockController {

    @GetMapping("reduct")
    public String reduct() {
        return "库存扣减";
    }
}
