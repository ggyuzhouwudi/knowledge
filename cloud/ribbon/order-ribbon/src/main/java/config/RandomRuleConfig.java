package config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 随机负载策略
 * @author Oliver
 * @date 2023年02月11日 21:03
 */
@Configuration
public class RandomRuleConfig {

    /**
     * 名字一定要叫iRule
     */
    @Bean
    @LoadBalanced
    public IRule iRule() {
        return new RandomRule();
    }
}
