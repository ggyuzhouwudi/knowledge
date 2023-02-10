package basic.sequential;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 顺序消费
 *
 * @author Oliver
 * @date 2023年02月10日 16:22
 */
public class SequentialConsumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("sync_group");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("order_topic", "order_tag");
        consumer.registerMessageListener((MessageListenerOrderly) (messages, consumeConcurrentlyContext) -> {
            for (MessageExt message : messages) {
                byte[] body = message.getBody();
                System.out.println(Thread.currentThread().getName() + ":" + new String(body));
            }
            return ConsumeOrderlyStatus.SUCCESS;
        });
        consumer.start();
        System.out.println("顺序消费者启动");
    }
}
