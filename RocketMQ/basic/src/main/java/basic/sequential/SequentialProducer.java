package basic.sequential;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

/**
 * 顺序发送：发送到同一个队列
 * 为什么会产生无序消费：
 * 1. broker里维护多个队列
 * 2. 生产者默认会轮训的将消息发送到不同队列
 * 3. 消费者多线程消费
 *
 * @author Oliver
 * @date 2023年02月10日 16:53
 */
public class SequentialProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("one_way_group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        List<Order> orders = Order.build();
        for (Order order : orders) {
            Message message = new Message("order_topic", "order_tag", (order.getId() + ":" + order.getName()).getBytes());
            /*
             * 参数一：消息对象
             * 参数二：消息队列的选择器
             * 参数三：选择队列的业务标识（比如订单ID）
             */
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                /**
                 * @param list 队列集合
                 * @param message 消息对象
                 * @param o 业务标识的参数
                 */
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    long orderId = (long) o;
                    long index = orderId % list.size();
                    return list.get((int) index);
                }
            }, order.getId());
            System.out.println("sendResult = " + sendResult);
        }

        // 关闭
        producer.shutdown();

    }

}
