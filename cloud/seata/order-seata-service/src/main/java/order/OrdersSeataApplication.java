package order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrdersSeataApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrdersSeataApplication.class, args);
    }

}
