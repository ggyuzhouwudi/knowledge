package basic.async;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * 异步消息，一般用于对响应时间敏感的业务场景，即发送端不能容忍长时间等待Broker的响应
 *
 * @author Oliver
 * @date 2023年02月10日 15:20
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        // 实例化消费生产者
        DefaultMQProducer producer = new DefaultMQProducer("async_group");
        // 指定NameServer地址，可以是多个，分号隔开
        // producer.setNamesrvAddr("localhost:9876;localhost:9877");
        producer.setNamesrvAddr("192.168.2.115:9876");
        // 启动producer
        producer.start();
        // 创建消息对象，指定主题topic，Tag和消息体
        Message message = new Message("async_basic", "async_tag", ("i am async body,num:").getBytes());
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送状态：" + sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        /*for (int i = 0; i < 10; i++) {
            // 创建消息对象，指定主题topic，Tag和消息体
            Message message = new Message("async_basic", "async_tag", ("i am async body,num:" + i).getBytes());
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送状态：" + sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }*/
        // 关闭
        producer.shutdown();

    }
}
