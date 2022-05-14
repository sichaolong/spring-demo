package henu.soft.study_nebula_graph_demo;

import com.vesoft.nebula.client.graph.exception.IOErrorException;
import henu.soft.study_nebula_graph_demo.pojo.player;
import henu.soft.study_nebula_graph_demo.utils.NebulaTemplate;
import henu.soft.study_nebula_graph_demo.vo.NebulaResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class NebulaTemplateTest {
    @Resource
    NebulaTemplate nebulaTemplate;




    @Test
    void addJSON() throws IOErrorException {
        log.info("开始像图空间添加一个点...[ v: {},name: {},age: {} ]","player103","xiaosi","21");

        String sql = "INSERT VERTEX player(name, age) VALUES \n" +
                "\t\"player103\":(\"xiaosi\", 21);";
        NebulaResult nebulaResult = nebulaTemplate.executeObject(sql);
        log.info("添加结果：{}",nebulaResult.toString());

    }


    @Test
    void findJson2() throws IOErrorException {
        // lookup 至少需要一个索引
        // String sql = "lookup on player  yield id(vertex) AS id,properties(vertex).name AS name,properties(vertex).age AS age;";

        String sql = "match (v:player) return id(v) as id,v.player.name as name,v.player.age as age limit 10;";
        NebulaResult<player> playerInfoNebulaResult = nebulaTemplate.queryObject(sql, player.class);
        log.info("查询包含player标签的所有点：{}" ,playerInfoNebulaResult.toString());
    }

}
