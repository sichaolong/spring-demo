
package henu.soft.xiaosi.controller;


import henu.soft.xiaosi.pojo.Department;
import henu.soft.xiaosi.pojo.Employee;
import henu.soft.xiaosi.service.DepartmentService;
import henu.soft.xiaosi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class EmployeeController {

    /**模拟数据阶段
     *      @Autowired
     *     private EmployeeDao employeeDao;
     *
     *     @Autowired
     *     DepartmentDao departmentDao;
     */

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/emps")
    public String list(Model model){
        Collection<Employee> employees = employeeService.getAll();
        model.addAttribute("emps",employees);
        return "emp/list";
    }

    //转到添加的表单
    @GetMapping("/add")
    public String toAddPage(Model model){

        //部门信息数据回显
        Collection<Department> departments = departmentService.getAll();
        model.addAttribute("departments",departments);
        return "emp/add";
    }
    //添加员工
    @PostMapping("/add")
    public String adding(Employee employee){
        employeeService.save(employee);

        return "redirect:/emps";

    }
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id,Model model){
        //数据回显
        Employee employee = employeeService.get(id);
        model.addAttribute("employee",employee);
        Department d = departmentService.get(id);
        model.addAttribute("d",d);


        Collection<Department> departments = departmentService.getAll();
        //System.out.println("debug===>"+departments.size());
        model.addAttribute("departments",departments);
        return "/emp/edit";


    }
    @PostMapping("/edit")
    public String edit(Employee editEmployee){

        employeeService.edit(editEmployee);
        return "redirect:/emps";


    }
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){

        employeeService.delete(id);
        return "redirect:/emps";


    }


}
