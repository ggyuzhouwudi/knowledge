package basic.deplay;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 延时消息，比如订单提交一个延时消息，1h再去检查这个订单状态，如果还是未付款就取消订单释放库存
 * 延迟级别：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
 * @author Oliver
 * @date 2023年02月10日 17:28
 */
public class DelayProducer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("delay_group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        for (int i = 0; i < 10; i++) {
            // 创建消息对象，指定主题topic，Tag和消息体
            Message message = new Message("delay_basic", "delay_tag", ("i am sync body,num:" + i).getBytes());
            message.setDelayTimeLevel(2);
            SendResult send = producer.send(message);
            String msgId = send.getMsgId();
            int queueId = send.getMessageQueue().getQueueId();
            System.out.println("发送状态：" + send + "消息ID：" + msgId + "队列ID：" + queueId);
        }
        // 关闭
        producer.shutdown();

    }
}
