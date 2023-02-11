package order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OrderApplication.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();
        System.out.println("environment.getProperty(\"user.name\") = " + environment.getProperty("user.name"));
    }

}
