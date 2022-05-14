package henu.soft.xiaosi.service;


import com.alibaba.fastjson.JSON;
import henu.soft.xiaosi.jsoup.HtmlParseUtil;
import henu.soft.xiaosi.pojo.Content;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ContentService {

    @Autowired
    RestHighLevelClient restHighLevelClient;


    // 1. 解析数据，放入es库中
    public Boolean getDataToEsByKeywords(String keywords) throws IOException {
        List<Content> contents = HtmlParseUtil.parseJD(keywords);

        BulkRequest request = new BulkRequest();
        request.timeout("2m");


        for (int i = 0; i < contents.size(); i++) {
            request.add(
                    new IndexRequest("goods_jd")
                            .source(JSON.toJSONString(contents.get(i)), XContentType.JSON)

            );

        }

        BulkResponse res = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        return !res.hasFailures();


    }

    // 2. 从es获取数据，实现搜索功能

    public List<Map<String,Object>> searchDataFromEs(String keywords,int pageNo,int pageSize) throws IOException {
        if(pageNo <= 1){
            pageNo = 1;
        }

        // 条件搜索
        SearchRequest request = new SearchRequest("goods_jd");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 分页
        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);

        // 精准匹配
        TermQueryBuilder name = QueryBuilders.termQuery("name", keywords);
        searchSourceBuilder.query(name);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        // 执行搜索
        request.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        // 解析结果

        List<Map<String,Object>> list = new ArrayList<>();

        for (SearchHit documentFields : response.getHits().getHits()) {
            list.add(documentFields.getSourceAsMap());
        }
        return list;
    }

    // 3. 搜索功能基础上实现高亮

    public List<Map<String, Object>> searchDataFromEsHighLight(String keywords, int pageNo, int pageSize) throws IOException {
        if (pageNo <= 1) {
            pageNo = 1;
        }

        // 条件搜索
        SearchRequest request = new SearchRequest("goods_jd");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 分页
        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);

        // 精准匹配
        TermQueryBuilder name = QueryBuilders.termQuery("name", keywords);
        searchSourceBuilder.query(name);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false); // 只高亮第一个显示的词
        highlightBuilder.field("name");
        highlightBuilder.preTags("<span style='color:pink'>");
        highlightBuilder.postTags("</span>");

        searchSourceBuilder.highlighter(highlightBuilder);


        // 执行搜索
        request.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        // 解析结果

        List<Map<String, Object>> list = new ArrayList<>();

        for (SearchHit hit : response.getHits().getHits()) {

            // 取出指定高亮字段

            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlightName = highlightFields.get("name");
            // 将原来字段替换
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            if (highlightName != null) {
                Text[] fragments = highlightName.fragments();
                String new_name = "";
                for (Text text : fragments) {
                    new_name += text;
                }
                sourceAsMap.put("name", new_name);
            }


            list.add(sourceAsMap);


        }
        return list;
    }

}
