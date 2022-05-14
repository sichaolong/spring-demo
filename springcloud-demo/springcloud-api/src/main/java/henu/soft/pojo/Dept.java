package henu.soft.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * dept 实体类
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)//链式写法
public class Dept implements Serializable {

    private Long deptno;
    private String dname;

    // 记录这个数据存在于哪个数据库，因为是微服务，一个服务对应一个数据库，同一个信息可能存在不同的数据库
    private String db_source;

    public Dept(String dname) {
        this.dname = dname;
    }
}
