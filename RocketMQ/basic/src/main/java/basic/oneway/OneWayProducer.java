package basic.oneway;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * 发送单向消息，生产者不是特别关心发送结果的场景，例如日志发送
 *
 * @author Oliver
 * @date 2023年02月10日 16:18
 */
public class OneWayProducer {

    public static void main(String[] args) throws Exception {
        // 实例化消费生产者
        DefaultMQProducer producer = new DefaultMQProducer("one_way_group");
        // 指定NameServer地址，可以是多个，分号隔开
        // producer.setNamesrvAddr("localhost:9876;localhost:9877");
        producer.setNamesrvAddr("localhost:9876");
        // 启动producer
        producer.start();

        for (int i = 0; i < 10; i++) {
            // 创建消息对象，指定主题topic，Tag和消息体
            Message message = new Message("one_way_basic", "one_way_tag", ("i am one way body,num:" + i).getBytes());
            producer.sendOneway(message);
        }
        // 关闭
        producer.shutdown();

    }
}
