package gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Oliver
 * @date 2023年02月19日 18:11
 */
@Component
public class CheckNameGatewayFilterFactory extends AbstractGatewayFilterFactory<CheckNameGatewayFilterFactory.Config> {
    public static final String NAME = "name";

    public CheckNameGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(NAME);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String name = exchange.getRequest().getQueryParams().getFirst("name");
            if (Objects.isNull(name) || config.getName().equals(name)) {
                return chain.filter(exchange);
            }
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().setComplete();
        };
    }

    public static class Config {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
