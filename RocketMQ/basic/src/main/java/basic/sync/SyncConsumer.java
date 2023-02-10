package basic.sync;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 默认负载均衡
 *
 * @author Oliver
 * @date 2023年02月10日 16:22
 */
public class SyncConsumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("sync_group");
        consumer.setNamesrvAddr("localhost:9876");
        // 设置为广播模式
        //consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.subscribe("sync_basic", "sync_tag");
        consumer.registerMessageListener((MessageListenerConcurrently) (messages, consumeConcurrentlyContext) -> {
            for (MessageExt message : messages) {
                byte[] body = message.getBody();
                System.out.println(new String(body));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
    }
}
