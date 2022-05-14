/*
package henu.soft.xiaosi.dao;

import henu.soft.xiaosi.pojo.Department;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Repository
public class DepartmentDao {
    //模拟数据库中的数据

    private static Map<Integer, Department> departmentMaps = null;
    static {
        //模拟部门表
        departmentMaps = new HashMap<>();
        departmentMaps.put(1001,new Department(1001,"教学部"));
        departmentMaps.put(1002,new Department(1002,"市场部"));
        departmentMaps.put(1003,new Department(1003,"教研部"));
        departmentMaps.put(1004,new Department(1004,"运行部"));
        departmentMaps.put(1005,new Department(1005,"后勤部"));
    }

    //获得所有部门的信息
    public Collection<Department> getDepartments(){
        return departmentMaps.values();
    }

    //通过id查询部门
    public Department getDepartmentById(Integer id){
        return departmentMaps.get(id);
    }
}

*/