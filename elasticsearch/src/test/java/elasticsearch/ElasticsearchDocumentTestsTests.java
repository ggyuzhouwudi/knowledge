package elasticsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import elasticsearch.entity.User;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;

/**
 * @author Oliver
 * @date 2023年01月11日 15:49
 */
public class ElasticsearchDocumentTestsTests extends ElasticsearchTests {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void createDocument() throws IOException {

        User user = User.builder().name("张三").age(18).build();
        IndexRequest request = new IndexRequest("user");
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.source(JSON.toJSONString(user), XContentType.JSON);
        //客户顿发送请求
        IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println("index.toString() = " + index.toString());
    }

    @Test
    public void getDocument() throws IOException {

        GetRequest request = new GetRequest("user", "1");
        //客户顿发送请求
        boolean exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);
        if (exists) {
            GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
            System.out.println("response = " + response.getSourceAsString());
        }
    }

    @Test
    public void updateDocument() throws IOException {

        User user = User.builder().name("张三PUT").age(18).build();
        UpdateRequest request = new UpdateRequest("user", "1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.doc(JSON.toJSONString(user), XContentType.JSON);
        UpdateResponse update = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println("update = " + update.status());
    }

    @Test
    public void deleteDocument() throws IOException {

        DeleteRequest request = new DeleteRequest("user", "1");
        DeleteResponse delete = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println("delete = " + delete.status());
    }

    @Test
    public void createBatchDocument() throws IOException {

        BulkRequest request = new BulkRequest();
        List<User> users = new ArrayList<>(5);
        users.add(User.builder().name("1").age(1).build());
        users.add(User.builder().name("2").age(2).build());
        users.add(User.builder().name("3").age(3).build());
        users.add(User.builder().name("4").age(4).build());
        users.add(User.builder().name("5").age(5).build());

        for (User user : users) {
            request.add(
                new IndexRequest("user").source(JSON.toJSONString(user), XContentType.JSON));
        }
        BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println("response = " + response.status());
    }

    @Test
    public void search() throws IOException {
        SearchRequest request = new SearchRequest("user");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 精准匹配
        // QueryBuilders.termQuery("name", "2");
        // QueryBuilders.matchAllQuery() 匹配所有

        // builder.from() ……

        TermQueryBuilder termQuery = QueryBuilders.termQuery("name", "2");
        builder.query(termQuery);
        SearchRequest searchRequest = request.source(builder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();
        for (SearchHit hit : hits) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }
    }

}
