package henu.soft.xiaosi.service;

import henu.soft.xiaosi.mapper.DepartmentMapper;
import henu.soft.xiaosi.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    //1. 查询所有部门
    public List<Department> getAll(){
        return departmentMapper.getDepartments();
    }

    //2. 根据员工id获取部门
    public Department get(Integer id){
        return departmentMapper.getDepartmentById(id);

    }
}
