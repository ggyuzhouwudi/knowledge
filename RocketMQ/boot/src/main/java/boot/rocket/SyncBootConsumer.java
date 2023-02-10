package boot.rocket;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 顺序消息生产者
 *
 * @author Oliver
 * @date 2023年02月10日 22:55
 */
@Component
@RocketMQMessageListener(consumerGroup = "boot-consumer-group",topic = "boot-sync-topic")
public class SyncBootConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        System.out.println("s = " + s);
    }
}
