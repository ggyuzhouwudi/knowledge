package basic.batch;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量消息
 * 能显著提高传递小消息的性能，限制：相同的topic、返回；不能是延时消息
 *
 * @author Oliver
 * @date 2023年02月10日 15:20
 */
public class BatchProducer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // 实例化消费生产者
        DefaultMQProducer producer = new DefaultMQProducer("batch_group");
        producer.setNamesrvAddr("localhost:9876");
        // 启动producer
        producer.start();

        List<Message> messages = new ArrayList<>(3);
        Message message = new Message("batch_basic", "batch_tag", ("i am batch body,num:1").getBytes());
        Message message2 = new Message("batch_basic", "batch_tag", ("i am batch body,num:2").getBytes());
        Message message3 = new Message("batch_basic", "batch_tag", ("i am batch body,num:2").getBytes());
        messages.add(message);
        messages.add(message2);
        messages.add(message3);

        ListMessageSplitter splitter = new ListMessageSplitter(messages);
        while (splitter.hasNext()) {
            List<Message> next = splitter.next();
            SendResult sendResult = producer.send(next);
            System.out.println("发送状态：" + sendResult);
        }
        // 关闭
        producer.shutdown();

    }
}
