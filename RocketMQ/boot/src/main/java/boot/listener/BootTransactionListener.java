package boot.listener;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Objects;

/**
 * @author Oliver
 * @date 2023年02月10日 23:24
 */
@RocketMQTransactionListener()
public class BootTransactionListener implements RocketMQLocalTransactionListener {
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        System.out.println("message = " + message);
        System.out.println("o = " + o);
        MessageHeaders headers = message.getHeaders();
        String tag = Objects.requireNonNull(headers.get("tag")).toString();
        if ("TAG1".equals(tag)) {
            return RocketMQLocalTransactionState.COMMIT;
        }

        if ("TAG2".equals(tag)) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        System.out.println("message = " + message);
        return RocketMQLocalTransactionState.COMMIT;
    }
}
