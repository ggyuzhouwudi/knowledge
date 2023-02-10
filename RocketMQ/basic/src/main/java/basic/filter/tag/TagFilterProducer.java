package basic.filter.tag;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 根据tag过滤
 *
 * @author Oliver
 * @date 2023年02月10日 15:20
 */
public class TagFilterProducer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("filter_tag_group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        for (int i = 0; i < 10; i++) {
            // 创建消息对象，指定主题topic，Tag和消息体
            Message message = new Message("filter_tag", "filter_tag_" + i, ("i am sync body,num:" + i).getBytes());
            SendResult send = producer.send(message);
            String msgId = send.getMsgId();
            int queueId = send.getMessageQueue().getQueueId();
            System.out.println("发送状态：" + send + "消息ID：" + msgId + "队列ID：" + queueId);
        }
        // 关闭
        producer.shutdown();

    }
}
