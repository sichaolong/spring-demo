package henu.soft.xiaosi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门表
 */
@Data//lombok注解，需要依赖lombok和插件
@AllArgsConstructor//自动生成有参构造
@NoArgsConstructor//无参构造
public class Department {
    private Integer id;
    private String departmentName;

}
