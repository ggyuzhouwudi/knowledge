package elasticsearch;

import java.io.IOException;
import javax.annotation.Resource;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.jupiter.api.Test;

/**
 * @author Oliver
 * @date 2023年01月11日 17:14
 */
public class ElasticsearchIndexTests extends ElasticsearchTests {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void createIndex() throws IOException {
        // 1 创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("user");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices()
            .create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.isAcknowledged());
        restHighLevelClient.close();
    }

    @Test
    public void getIndex() throws IOException {
        // 1 创建索引请求
        GetIndexRequest request = new GetIndexRequest("fruit");
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        if (exists) {
            GetIndexResponse getIndexResponse = restHighLevelClient.indices()
                .get(request, RequestOptions.DEFAULT);
            System.out.println(getIndexResponse);
        }
        restHighLevelClient.close();
    }

    @Test
    public void deleteIndex() throws IOException {
        // 1 创建索引请求
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("fruit");
        AcknowledgedResponse delete = restHighLevelClient.indices()
            .delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println("delete = " + delete.isAcknowledged());
        restHighLevelClient.close();
    }

}
