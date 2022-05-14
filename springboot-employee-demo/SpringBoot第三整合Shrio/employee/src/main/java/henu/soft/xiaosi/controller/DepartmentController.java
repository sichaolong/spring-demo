package henu.soft.xiaosi.controller;

import henu.soft.xiaosi.mapper.DepartmentMapper;
import henu.soft.xiaosi.pojo.Department;
import henu.soft.xiaosi.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    //1. 查询全部部门
    @RequestMapping("/getDepartments")
    public List<Department> getDepartments(){
        return departmentService.getAll();
    }

    //2. 根据部门id查询部门
    @RequestMapping("/getDepartmentById/{id}")
    public Department getDepartmentById(@PathVariable("id")  Integer id){
        return departmentService.get(id);
    }

}
