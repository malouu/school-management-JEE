package com.pi.managedBeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import com.pi.dao.DepartmentDao;
import com.pi.entities.Department;

@ManagedBean(name = "departmentMB")
@ViewScoped

public class DepartmentMB {
    private Department department = new Department();
    private Department selectedDepartment = new Department();
    private List<Department> departments;
    DepartmentDao departmentDao = new DepartmentDao();

    public String add() {
        departmentDao.add(department);
        return "department.xhtml?faces-redirect=true";
    }

    public String update() {
        departmentDao.update(selectedDepartment);
        return "department.xhtml?faces-redirect=true";
    }

    public String delete() {
        departmentDao.delete(selectedDepartment);
        return "department.xhtml?faces-redirect=true";
    }

    public void initDataTable() {
        departments = departmentDao.getAllDepartments();
    }

    public void initForm() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

        Department department = new Department();
        department = departmentDao.getDepartmentById(Integer.parseInt(id));
        if (department != null) {
            this.selectedDepartment = department;
        }
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Department getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(Department selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

}
