package basic.sync;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 同步消息发送,没收到回复时等待，一般用于发送重要通知等等
 *
 * @author Oliver
 * @date 2023年02月10日 15:20
 */
public class SyncProducer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // 实例化消费生产者
        DefaultMQProducer producer = new DefaultMQProducer("sync_group");
        // 指定NameServer地址，可以是多个，分号隔开
        // producer.setNamesrvAddr("localhost:9876;localhost:9877");
        producer.setNamesrvAddr("localhost:9876");
        // 启动producer
        producer.start();

        for (int i = 0; i < 10; i++) {
            // 创建消息对象，指定主题topic，Tag和消息体
            Message message = new Message("sync_basic", "sync_tag", ("i am sync body,num:" + i).getBytes());
            SendResult send = producer.send(message);
            String msgId = send.getMsgId();
            int queueId = send.getMessageQueue().getQueueId();
            System.out.println("发送状态：" + send + "消息ID：" + msgId + "队列ID：" + queueId);
        }
        // 关闭
        producer.shutdown();

    }
}
