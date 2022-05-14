package henu.soft.study_nebula_graph_demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vesoft.nebula.client.graph.net.Session;
import henu.soft.study_nebula_graph_demo.constant.NebulaConstant;
import henu.soft.study_nebula_graph_demo.vo.NebulaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 封装一个Nebula操作工具类
 */

@Slf4j
@Component
public class NebulaTemplate {

    @Resource
    Session session;


    /**
     * 1. 封装查询方法
     * @param stmt 查询语句
     * @param tClass 返回数据的Class
     * @param <T> 数据类型
     * @return
     */
    public <T> NebulaResult<T> queryObject(String stmt, Class<T> tClass) {
        NebulaResult<T> nebulaResult = executeObject(stmt);
        // 无返回数据，直接返回
        if (Objects.isNull(nebulaResult.getData())) {
            return nebulaResult;
        }
        // 有返回数据，转为json返回
        Optional.ofNullable(nebulaResult.getData()).ifPresent(data -> nebulaResult.setData(data.stream().map(d -> JSONObject.toJavaObject(((JSONObject) d), tClass)).collect(Collectors.toList())));
        return nebulaResult;
    }

    public NebulaResult executeObject(String stmt) {
        // 执行
        JSONObject jsonObject = executeJson(stmt);
        return JSONObject.toJavaObject(jsonObject, NebulaResult.class);
    }

    public JSONObject executeJson(String stmt) {
        // 1. 执行结果
        JSONObject restJson = new JSONObject();
        try {
            // 2. 执行nGQL
            JSONObject jsonObject = JSON.parseObject(Objects.requireNonNull(session).executeJson(stmt));
            // 3. 执行是否有错误
            JSONObject errors = jsonObject.getJSONArray(NebulaConstant.NebulaJson.ERRORS.getKey()).getJSONObject(0);

            // 4. 执行结果code放入
            restJson.put(NebulaConstant.NebulaJson.CODE.getKey(), errors.getInteger(NebulaConstant.NebulaJson.CODE.getKey()));

            // 5. 执行错误，需要放入错误信息，然后直接返回
            if (errors.getInteger(NebulaConstant.NebulaJson.CODE.getKey()) != 0) {
                restJson.put(NebulaConstant.NebulaJson.MESSAGE.getKey(), errors.getString(NebulaConstant.NebulaJson.MESSAGE.getKey()));
                return restJson;
            }
            // 6. 执行结果results放入
            JSONObject results = jsonObject.getJSONArray(NebulaConstant.NebulaJson.RESULTS.getKey()).getJSONObject(0);
            JSONArray columns = results.getJSONArray(NebulaConstant.NebulaJson.COLUMNS.getKey());
            if (Objects.isNull(columns)) {
                return restJson;
            }
            JSONArray data = results.getJSONArray(NebulaConstant.NebulaJson.DATA.getKey());
            if (Objects.isNull(data)) {
                return restJson;
            }
            List<JSONObject> resultList = new ArrayList<>();

            data.stream().map(d -> (JSONObject) d).forEach(d -> {
                JSONArray row = d.getJSONArray(NebulaConstant.NebulaJson.ROW.getKey());
                JSONObject map = new JSONObject();
                for (int i = 0; i < columns.size(); i++) {
                    map.put(columns.getString(i), row.get(i));
                }
                resultList.add(map);
            });
            restJson.put(NebulaConstant.NebulaJson.DATA.getKey(), resultList);

        } catch (Exception e) {
            restJson.put(NebulaConstant.NebulaJson.CODE.getKey(), NebulaConstant.ERROR_CODE);
            restJson.put(NebulaConstant.NebulaJson.MESSAGE.getKey(), e.toString());
            log.error("nebula execute err：", e);
        }
        return restJson;
    }
}
