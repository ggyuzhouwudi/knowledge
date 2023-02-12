package sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
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
    // add:要保护的资源  blockHandler:触发后执行的方法
    @SentinelResource(value = "add", blockHandler = "addBlockHandler")
    public String add() {
        return "add";
    }

    /**
     * 注意：1。必须是public 2.返回值必须一样 3.BlockException 这个要写对，还有个security的BlockedException，容易混淆
     */
    public String addBlockHandler(BlockException e) {
        e.printStackTrace();
        return "被流控了";

    }

    @GetMapping("slow")
    public String slow() throws InterruptedException {
        Thread.sleep(3000);
        return "正常访问";
    }

    @GetMapping("exception")
    public String exception() {
        int i = 1/0;
        return "正常访问";
    }
}
