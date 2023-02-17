package persistence.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Oliver
 * @date 2023年02月11日 21:21
 */
@RestController
@RequestMapping("order")
public class OrderController {
    @GetMapping("add")
    public String add() {
        return "add";
    }
}
