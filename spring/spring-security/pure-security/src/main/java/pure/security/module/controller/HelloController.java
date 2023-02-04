package pure.security.module.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Oliver
 * @date 2023年01月06日 14:37
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("hello security");
        return "hello security";
    }
}
