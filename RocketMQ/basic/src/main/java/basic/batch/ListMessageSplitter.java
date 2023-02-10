package basic.batch;

import org.apache.rocketmq.common.message.Message;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * List迭代分割器，用于批量消息切割,因为RocketMQ批量发送消息的时候消息不能超过4M
 *
 * @author Oliver
 * @date 2023年02月10日 18:10
 */
public class ListMessageSplitter implements Iterator<List<Message>> {

    private final int SIZE_LIMIT = 1024 * 1024 * 4;
    private final List<Message> messages;
    private int currIndex;

    public ListMessageSplitter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean hasNext() {
        return currIndex < messages.size();
    }

    @Override
    public List<Message> next() {
        int nextIndex = currIndex;
        int totalSize = 0;
        for (; nextIndex < messages.size(); nextIndex++) {
            Message message = messages.get(nextIndex);
            int tmpSize = message.getTopic().length() + message.getBody().length;
            Map<String, String> properties = message.getProperties();
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                tmpSize += entry.getKey().length() + entry.getValue().length();
            }
            tmpSize += 20; // 增加日志开销的20字节
            if (tmpSize > SIZE_LIMIT) {
                //单个消息超过了最大限制，忽略，否则会阻塞分裂的过程
                if (nextIndex - currIndex == 0) {
                    // 假如下一个子列表没有元素，则添加这个子表然后退出循环，否则只是退出循环
                    nextIndex++;
                }
                break;
            }
            if (tmpSize + totalSize > SIZE_LIMIT) {
                break;
            } else {
                totalSize += tmpSize;
            }
        }
        List<Message> subList = messages.subList(currIndex, nextIndex);
        currIndex = nextIndex;
        return subList;
    }


}
