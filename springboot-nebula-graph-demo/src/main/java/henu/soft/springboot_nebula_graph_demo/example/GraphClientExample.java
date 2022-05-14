package henu.soft.springboot_nebula_graph_demo.example;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vesoft.nebula.Date;
import com.vesoft.nebula.ErrorCode;
import com.vesoft.nebula.Value;
import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.CASignedSSLParam;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.data.SelfSignedSSLParam;
import com.vesoft.nebula.client.graph.data.ValueWrapper;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import com.vesoft.nebula.client.graph.net.Session;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GraphClientExample {

    //    打印结果函数
    private static void printResult(ResultSet resultSet) throws UnsupportedEncodingException {
        List<String> colNames = resultSet.keys();
        for (String name : colNames) {
            System.out.printf("%15s |", name);
        }
        System.out.println();
        for (int i = 0; i < resultSet.rowsSize(); i++) {
            ResultSet.Record record = resultSet.rowValues(i);
            for (ValueWrapper value : record.values()) {
                if (value.isLong()) {
                    System.out.printf("%15s |", value.asLong());
                }
                if (value.isBoolean()) {
                    System.out.printf("%15s |", value.asBoolean());
                }
                if (value.isDouble()) {
                    System.out.printf("%15s |", value.asDouble());
                }
                if (value.isString()) {
                    System.out.printf("%15s |", value.asString());
                }
                if (value.isTime()) {
                    System.out.printf("%15s |", value.asTime());
                }
                if (value.isDate()) {
                    System.out.printf("%15s |", value.asDate());
                }
                if (value.isDateTime()) {
                    System.out.printf("%15s |", value.asDateTime());
                }
                if (value.isVertex()) {
                    System.out.printf("%15s |", value.asNode());
                }
                if (value.isEdge()) {
                    System.out.printf("%15s |", value.asRelationship());
                }
                if (value.isPath()) {
                    System.out.printf("%15s |", value.asPath());
                }
                if (value.isList()) {
                    System.out.printf("%15s |", value.asList());
                }
                if (value.isSet()) {
                    System.out.printf("%15s |", value.asSet());
                }
                if (value.isMap()) {
                    System.out.printf("%15s |", value.asMap());
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // 1. nebula连接池对象
        NebulaPool pool = new NebulaPool();
        // 2. nebula连接会话
        Session session;


        try {
            // 3. 连接处配置信息类
            NebulaPoolConfig nebulaPoolConfig = new NebulaPoolConfig();
            nebulaPoolConfig.setMaxConnSize(100);
            // 4. 服务端地址
            List<HostAddress> addresses = Arrays.asList(new HostAddress("127.0.0.1", 9669));

            // 5. 连接池加载配置初始化服务端连接
            Boolean initResult = pool.init(addresses, nebulaPoolConfig);

            // 池初始化保存直接退出
            if (!initResult) {
                log.error("pool init failed.");
                return;
            }

            // 6. 获得连接会话session
            session = pool.getSession("root", "nebula", false);


            // 7.1 创建test图空间、person标签(包含name、age属性)、like边(包含属性likeness)
            {
                String createSchema = "CREATE SPACE IF NOT EXISTS test(vid_type=fixed_string(20)); "
                        + "USE test;"
                        + "CREATE TAG IF NOT EXISTS person(name string, age int);"
                        + "CREATE EDGE IF NOT EXISTS like(likeness double)";
                ResultSet resp = session.execute(createSchema);
                if (!resp.isSucceeded()) {
                    log.error(String.format("Execute: `%s', failed: %s",
                            createSchema, resp.getErrorMessage()));
                    System.exit(1);
                }
            }

            TimeUnit.SECONDS.sleep(5);

            // 7.2 插入多个点，每个点代表一个人物，包含name和age
            {
                String insertVertexes = "INSERT VERTEX person(name, age) VALUES "
                        + "'Bob':('Bob', 10), "
                        + "'Lily':('Lily', 9), "
                        + "'Tom':('Tom', 10), "
                        + "'Jerry':('Jerry', 13), "
                        + "'John':('John', 11);";
                ResultSet resp = session.execute(insertVertexes);
                if (!resp.isSucceeded()) {
                    log.error(String.format("Execute: `%s', failed: %s",
                            insertVertexes, resp.getErrorMessage()));
                    System.exit(1);
                }
            }

            // 7.3 插入多个边，每个边包含属性likeness
            {
                String insertEdges = "INSERT EDGE like(likeness) VALUES "
                        + "'Bob'->'Lily':(80.0), "
                        + "'Bob'->'Tom':(70.0), "
                        + "'Lily'->'Jerry':(84.0), "
                        + "'Tom'->'Jerry':(68.3), "
                        + "'Bob'->'John':(97.2);";
                ResultSet resp = session.execute(insertEdges);
                if (!resp.isSucceeded()) {
                    log.error(String.format("Execute: `%s', failed: %s",
                            insertEdges, resp.getErrorMessage()));
                    System.exit(1);
                }
            }


            // 7.4 go查询，从点Bob开始，$^表示边的起点，查询出来name、age、likeness
            {
                String query = "GO FROM \"Bob\" OVER like "
                        + "YIELD $^.person.name, $^.person.age, like.likeness";
                ResultSet resp = session.execute(query);
                if (!resp.isSucceeded()) {
                    log.error(String.format("Execute: `%s', failed: %s",
                            query, resp.getErrorMessage()));
                    System.exit(1);
                }
                printResult(resp);
            }


            // 7.5 Map参数查询
            {
                // prepare parameters
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("p1", 3);
                paramMap.put("p2", true);
                paramMap.put("p3", 3.3);


                // Value类型，保存到list和map中
                Value nvalue = new Value();
                Date date = new Date();
                date.setYear((short) 2021);
                nvalue.setDVal(date);


                // value为List类型
                List<Object> list = new ArrayList<>();
                list.add(1);
                list.add(true);
                list.add(nvalue);
                list.add(date);
                paramMap.put("p4", list);

                // value为Map类型
                Map<String, Object> map = new HashMap<>();
                map.put("a", 1);
                map.put("b", true);
                map.put("c", nvalue);
                map.put("d", list);
                paramMap.put("p5", map);

                String query = "RETURN abs($p1+1),toBoolean($p2) and false,$p3,$p4[2],$p5.d[3]";
                ResultSet resp = session.executeWithParameter(query, paramMap);

                if (!resp.isSucceeded()) {
                    log.error(String.format("Execute: `%s', failed: %s",
                            query, resp.getErrorMessage()));
                    System.exit(1);
                }
                printResult(resp);
            }

            // 7.6 查询
            {

                String queryForJson = "YIELD 1";
                String resp = session.executeJson(queryForJson);

                // 结果以json格式返回，取出来errors
                JSONObject errors = JSON.parseObject(resp).getJSONArray("errors").getJSONObject(0);
                if (errors.getInteger("code") != 0) {
                    log.error(String.format("Execute: `%s', failed: %s",
                            queryForJson, errors.getString("message")));
                    System.exit(1);
                }
                System.out.println(resp);
            }

            // 8.1 配置连接池ssl加密
            {
                // 池配置
                NebulaPoolConfig nebulaSslPoolConfig = new NebulaPoolConfig();
                nebulaSslPoolConfig.setMaxConnSize(100);
                nebulaSslPoolConfig.setEnableSsl(true);
                // 指定ssl加密文件的位置
                nebulaSslPoolConfig.setSslParam(new CASignedSSLParam(
                        "examples/src/main/resources/ssl/casigned.pem",
                        "examples/src/main/resources/ssl/casigned.crt",
                        "examples/src/main/resources/ssl/casigned.key"));
                NebulaPool sslPool = new NebulaPool();

                // 开启ssl加密的池初始化
                sslPool.init(Arrays.asList(new HostAddress("127.0.0.1", 9669)),
                        nebulaSslPoolConfig);

                // 查询
                String queryForJson = "YIELD 1";
                Session sslSession = sslPool.getSession("root", "nebula", false);
                String resp = sslSession.executeJson(queryForJson);
                JSONObject errors = JSON.parseObject(resp).getJSONArray("errors").getJSONObject(0);
                if (errors.getInteger("code") != ErrorCode.SUCCEEDED.getValue()) {
                    log.error(String.format("Execute: `%s', failed: %s",
                            queryForJson, errors.getString("message")));
                    System.exit(1);
                }
                System.out.println(resp);
            }

            // 8.2 配置连接池ssl加密

            {
                NebulaPoolConfig nebulaSslPoolConfig = new NebulaPoolConfig();
                nebulaSslPoolConfig.setMaxConnSize(100);
                nebulaSslPoolConfig.setEnableSsl(true);
                // 指定ssl加密文件的位置
                nebulaSslPoolConfig.setSslParam(new SelfSignedSSLParam(
                        "examples/src/main/resources/ssl/selfsigned.pem",
                        "examples/src/main/resources/ssl/selfsigned.key",
                        "vesoft"));
                NebulaPool sslPool = new NebulaPool();
                sslPool.init(Arrays.asList(new HostAddress("127.0.0.1", 9669)),
                        nebulaSslPoolConfig);
                String queryForJson = "YIELD 1";
                Session sslSession = sslPool.getSession("root", "nebula", false);
                String resp = sslSession.executeJson(queryForJson);
                JSONObject errors = JSON.parseObject(resp).getJSONArray("errors").getJSONObject(0);
                if (errors.getInteger("code") != ErrorCode.SUCCEEDED.getValue()) {
                    log.error(String.format("Execute: `%s', failed: %s",
                            queryForJson, errors.getString("message")));
                    System.exit(1);
                }
                System.out.println(resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

