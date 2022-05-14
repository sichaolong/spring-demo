package henu.soft.controller;

import henu.soft.pojo.Dept;
import henu.soft.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeptController {

    @Autowired
    DeptService deptService;


    @PostMapping("/dept/add")
    public boolean addDept(Dept dept){
        boolean result = deptService.addDept(dept);
        return result;

    }

    @GetMapping("/dept/get/{id}")
    public Dept get(@PathVariable Long id){
        Dept dept = deptService.queryById(id);
        System.out.println(dept);
        return dept;
    }
    @GetMapping("/dept/all")
    public List<Dept> getAll(){
        List<Dept> all = deptService.findAll();

        System.out.println(all);
        return all;
    }
}
