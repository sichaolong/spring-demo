package henu.soft.springboot_nebula_graph_demo.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 返回结果封装
 * @param <T>
 */
@Data
@ToString
public class NebulaResult<T> {
    private Integer code;
    private String message;
    private List<T> data;

    public boolean isSuccessed(){
        return code == 0;
    }


}
