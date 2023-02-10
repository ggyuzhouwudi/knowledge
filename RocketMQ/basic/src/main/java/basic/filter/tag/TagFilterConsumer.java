package basic.filter.tag;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 根据Tag过滤
 *
 * @author Oliver
 * @date 2023年02月10日 16:22
 */
public class TagFilterConsumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("filter_tag_group");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("filter_tag", "filter_tag_1 || filter_tag_2");
        consumer.registerMessageListener((MessageListenerConcurrently) (messages, consumeConcurrentlyContext) -> {
            for (MessageExt message : messages) {
                byte[] body = message.getBody();
                System.out.println(new String(body));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        System.out.println("消费者启动……");
    }
}
