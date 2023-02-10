import boot.RocketMQBootApplication;
import boot.rocket.SyncBootProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author Oliver
 * @date 2023年02月10日 22:59
 */
@SpringBootTest(classes = RocketMQBootApplication.class)
public class ProducerTests {

    @Resource
    private SyncBootProducer syncBootProducer;

    @Test
    public void send() {
        syncBootProducer.send("boot-sync-topic", "boot-sync-topic");
        System.out.println("消息发送成功");
    }

    @Test
    public void sendInTransaction() {
        syncBootProducer.sendInTransaction("topic-boot-transaction", "transaction-message");
        System.out.println("事务消息发送成功");
    }
}
