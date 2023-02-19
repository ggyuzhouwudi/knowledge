package stock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stock.service.StockService;

/**
 * @author Oliver
 * @date 2023年02月18日 19:51
 */
@RestController
@RequestMapping("stock")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StockController {

    private final StockService service;

    @GetMapping("reduct")
    public Object reduct(@RequestParam("productId") Long productId) {
        return service.reduct(productId);
    }
}
