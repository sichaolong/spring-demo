package henu.soft.xiaosi;

import com.alibaba.fastjson.JSON;
import henu.soft.xiaosi.pojo.User;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
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
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class StudyElasticSearchApplicationTests {


    @Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    void contextLoads() {
    }

    // 创建索引
    @Test
    void testCreateIndexRequest() throws IOException {

        // 1. 创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("xiaosi_index");

        // 2. 客户端执行请求 IndicesClient ，请求后获得响应
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);


    }

    // 判断索引是否存在
    @Test
    void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("xiaosi_index");

        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        if (exists == true) {
            System.out.println("索引存在！");
        } else {
            System.out.println("索引不存在！");
        }


    }

    // 删除索引
    @Test
    void testDeleteIndex() throws IOException {

        DeleteIndexRequest request = new DeleteIndexRequest("xiaosi_index");

        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);

        System.out.println(response.isAcknowledged());
    }

    // 测试文档操作

    // 增加文档
    @Test
    void testAddDocument() throws IOException {
        User user = new User("xiaosi", 20);

        // 找到已存在的索引
        IndexRequest request = new IndexRequest("xiaosi_index");


        // put /xiaosi_idnex/_doc/1
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");


        // json序列化
        request.source(JSON.toJSONString(user), XContentType.JSON);

        // 客户端发送请求
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(response.status().getStatus());//201

    }


    // 判断文档是否存在
    @Test
    void testExistsDocument() throws IOException {
        GetRequest request = new GetRequest("xiaosi_index", "1");
        // 很多api方法可以点出来
         request.storedFields("_none_");
        boolean exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);

        System.out.println(exists);
    }

    // 查询文档
    @Test
    void testGetDocument() throws IOException {
        GetRequest request = new GetRequest("xiaosi_index", "1");
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
        System.out.println(response);
        //System.out.println(response);
    }
    // 更新文档
    @Test
    void testUpdateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest("xiaosi_index", "1");

        request.timeout("1s");

        User newUser = new User("xiaoxiao",10);
        request.doc(JSON.toJSONString(newUser),XContentType.JSON);

        System.out.println(restHighLevelClient.update(request, RequestOptions.DEFAULT));


    }

    // 删除文档
    @Test
    void testDeleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("xiaosi_index", "1");

        DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
        System.out.println(response);
    }

    // 批量list插入文档

    @Test
    void testBulkAddDocument() throws IOException {
        BulkRequest request = new BulkRequest("xiaosi_index");

        request.timeout("10s");

        List<User> list = new ArrayList<>();
        User user1 = new User("xiaosi1",1);
        User user2 = new User("xiaosi2",2);
        User user3 = new User("xiaosi3",3);
        User user4 = new User("xiaosi4",4);

        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);


        for (int i = 0; i < 4; i++) {
            request.add(
                        new IndexRequest("xiaosi_index")
                        .id("" + (i+1))
                        .source(JSON.toJSONString(list.get(i)),XContentType.JSON)
            );
        }

        BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response);
        System.out.println(response.hasFailures());

    }


    // 条件查询
    @Test
    void testSearchDocument() throws IOException {
        SearchRequest request = new SearchRequest("xiaosi_index");

        // 构建查询条件器
        SearchSourceBuilder ssb = new SearchSourceBuilder();


        // 设置查询方式、条件
        TermQueryBuilder termQuery = QueryBuilders.termQuery("name", "xiaosi");
        //QueryBuilders.boolQuery()
        //QueryBuilders.matchAllQuery()
        ssb.query(termQuery);


        ssb.timeout(new TimeValue(60, TimeUnit.SECONDS));


        // 组合查询条件器到request
        request.source(ssb);

        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        System.out.println(JSON.toJSONString(response.getHits()));


        System.out.println("==================");

        for (SearchHit hit : response.getHits().getHits()) {

            System.out.println(hit.getSourceAsMap());
        }

    }




}
