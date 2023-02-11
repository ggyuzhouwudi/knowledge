package order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*@RibbonClients(value = {
        // 当调用stock-ribbon-service服务时使用随机
        @RibbonClient(name = "stock-ribbon-service", configuration = RandomRuleConfig.class)
})*/
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
