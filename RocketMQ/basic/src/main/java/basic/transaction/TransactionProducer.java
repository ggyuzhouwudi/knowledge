package basic.transaction;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 事务发送
 *
 * @author Oliver
 * @date 2023年02月10日 15:20
 */
public class TransactionProducer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        TransactionMQProducer producer = new TransactionMQProducer("transaction_group");
        producer.setNamesrvAddr("localhost:9876");
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 本地事务操作
             * @param message 消息
             * @param o
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                if ("TAG1".equals(message.getTags())) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                if ("TAG2".equals(message.getTags())) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                if ("TAG3".equals(message.getTags())) {
                    return LocalTransactionState.UNKNOW;
                }
                return null;
            }

            /**
             * rocketmq回查方法
             * @param messageExt 消息扩展对象
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("我的TAG:" + messageExt.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        producer.start();
        String[] tags = {"TAG1","TAG2","TAG3"};
        for (int i = 0; i < tags.length; i++) {
            Message message = new Message("transaction_basic", tags[i], ("i am transaction body,num:" + i).getBytes());
            // 传null针对整个producer进行事务管理，也可以对某个topic进行管理
            SendResult send = producer.sendMessageInTransaction(message, null);
            String msgId = send.getMsgId();
            int queueId = send.getMessageQueue().getQueueId();
            System.out.println("发送状态：" + send + "消息ID：" + msgId + "队列ID：" + queueId);
        }
        // 关闭
        //producer.shutdown();

    }
}
