package basic.filter.sql;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 根据sql过滤:找到RocketMQ的安装目录的conf/2m-2s-async下的broker-a.properties主配置文件，增加如下配置
 * #是否支持根据属性过滤 如果使用基于标准的sql92模式过滤消息则改参数必须设置为true
 * enablePropertyFilter=true
 *
 * @author Oliver
 * @date 2023年02月10日 16:22
 */
public class SqlFilterConsumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("filter_sql_group");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("filter_sql", MessageSelector.bySql("i>5"));
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
