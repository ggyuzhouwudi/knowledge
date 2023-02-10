package boot.rocket;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 顺序消息生产者
 *
 * @author Oliver
 * @date 2023年02月10日 22:55
 */
@Component
public class SyncBootProducer {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void send(String topic, String message) {
        rocketMQTemplate.convertAndSend(topic, message);
    }

    public void sendInTransaction(String topic, String message) {
        String[] tags = {"TAG1", "TAG2", "TAG3"};
        for (int i = 0; i < tags.length; i++) {
            Message<String> msg = MessageBuilder.withPayload(message).setHeader("tag", tags[i]).build();
            TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(topic + i, msg, i);
            System.out.println("transactionSendResult:" + transactionSendResult);
        }
    }
}
