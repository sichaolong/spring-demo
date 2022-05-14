package henu.soft.service;

import henu.soft.dao.DeptDao;
import henu.soft.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {
    @Autowired
    DeptDao deptDao;

    public boolean addDept(Dept dept){
        return deptDao.addDept(dept);

    }

    public Dept queryById(Long id){
        return deptDao.queryById(id);

    }

    public List<Dept> findAll(){
        return deptDao.findAll();

    }
}
