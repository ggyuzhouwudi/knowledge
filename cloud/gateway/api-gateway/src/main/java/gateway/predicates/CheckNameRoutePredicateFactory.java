package gateway.predicates;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Oliver
 * @date 2023年02月19日 17:40
 */
@Component
public class CheckNameRoutePredicateFactory extends AbstractRoutePredicateFactory<CheckNameRoutePredicateFactory.Config> {

    public static final String CHECK_NAME_KEY = "name";

    public CheckNameRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(CHECK_NAME_KEY);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return (GatewayPredicate) serverWebExchange -> {
            if ("z".equals(config.name)) {
                return true;
            }
            return false;
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