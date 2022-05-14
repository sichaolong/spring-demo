/*
package henu.soft.xiaosi.dao;


import henu.soft.xiaosi.pojo.Department;
import henu.soft.xiaosi.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

@Repository//dao层的注解
public class EmployeeDao {
    //模拟员工数据表

    private static HashMap<Integer, Employee> employeeMaps = null;
    @Autowired
    private DepartmentDao departmentDao;
    static{
        employeeMaps = new HashMap<>();
        employeeMaps.put(1925050351,new Employee(1925050351,"小A","123456@qq.com",1,new Department(1001,"后勤部"),new Date()));
        employeeMaps.put(1925050352,new Employee(1925050352,"小B","12345@qq.com",0,new Department(1002,"后勤部"),new Date()));
        employeeMaps.put(1925050353,new Employee(1925050353,"小C","1234@qq.com",1,new Department(1004,"后勤部"),new Date()));
        employeeMaps.put(1925050354,new Employee(1925050354,"小D","123@qq.com",1,new Department(1003,"后勤部"),new Date()));

    }

    //模拟主键自增
    private static Integer initId = 1925050355;
    //模拟增加一个员工
    public void save(Employee employee){
        if(employee.getId() == null){
            employee.setId(initId++);
        }
        //设置新增员工的部门
        employee.setDepartment(departmentDao.getDepartmentById(employee.getDepartment().getId()));
        employeeMaps.put(employee.getId(),employee);
    }

    //查询全部员工
    public Collection<Employee> getAll(){
        return employeeMaps.values();
    }

    //通过id查询员工
    public Employee getEmployeeById(Integer id){
        return employeeMaps.get(id);
    }

    //删除员工
    public void delete(Integer id){
        employeeMaps.remove(id);
    }


}

 */
