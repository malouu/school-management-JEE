package com.pi.services;

import com.pi.dao.DepartmentDao;
import com.pi.entities.Department;

public class DepartmentService {
    private final DepartmentDao depDao;
    public DepartmentService(DepartmentDao depDao) {
        this.depDao = depDao;
    }
    //add inexistent department
    public void addDepartment(Department dep) {
        if(depDao.getDepartmentById(dep.getId()) == null) {
            depDao.add(dep);
        }
        throw new RuntimeException("Department already exists, The ID was found in the database");
        
    }
    //update department
    public void updateDepartment(Department dep) {
        if(depDao.getDepartmentById(dep.getId()) != null) {
            depDao.update(dep);
        }
        throw new RuntimeException("Department does not exist, The ID was not found in the database");
    }
    //delete department
    public void deleteDepartment(Department dep) {
        if(depDao.getDepartmentById(dep.getId()) != null) {
            depDao.delete(dep);
        }
        throw new RuntimeException("Department does not exist, The ID was not found in the database");
    }
    

}
