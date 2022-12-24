package com.pi.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import javax.faces.bean.ViewScoped;
import com.pi.dao.DepartmentDao;
import com.pi.entities.Department;

@ManagedBean(name = "departmentMB")
@Named(value = "departmentMB")
@ViewScoped

public class DepartmentMB implements Serializable {
    private Department department = new Department();
    private Department selecteddepartment = new Department();
    private List<Department> departments;
    private List<Department> selecteddepartments;

    @Inject
    DepartmentDao departmentDao = new DepartmentDao();

    public String add() {
        departmentDao.add(department);
        return "department.xhtml?faces-redirect=true";
    }

    public String update() {
        departmentDao.update(selecteddepartment);
        return "department.xhtml?faces-redirect=true";
    }

    public String delete() {
        departmentDao.delete(selecteddepartment);
        return "department.xhtml?faces-redirect=true";
    }

    public void initDataTable() {
        departments = departmentDao.getAllDepartments();
    }

    public void initForm() {
        int SubscrpNumber;
        SubscrpNumber = Integer.parseInt(
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("code"));
        Department department = new Department();
        department = departmentDao.getDepartmentById(SubscrpNumber);
        System.out.print(department.toString());
        if (department != null) {
            this.selecteddepartment = department;
        }
    }

    public Department getdepartment() {
        return department;
    }

    public void setdepartment(Department department) {
        this.department = department;
    }

    public List<Department> getdepartments() {
        return departments;
    }

    public void setdepartments(List<Department> departments) {
        this.departments = departments;
    }

    public Department getSelecteddepartment() {
        return selecteddepartment;
    }

    public void setSelecteddepartment(Department selecteddepartment) {
        this.selecteddepartment = selecteddepartment;
    }

    public List<Department> getSelecteddepartments() {
        return selecteddepartments;
    }

    public void setSelecteddepartments(List<Department> selecteddepartments) {
        this.selecteddepartments = selecteddepartments;
    }

    public void openNew() {
    	this.selecteddepartment= new Department();
    }

    

    public boolean hasSelectedDepartment() {
        return this.selecteddepartment != null;
    }

    public boolean hasSelectedDepartments() {
        return this.selecteddepartments != null && !this.selecteddepartments.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedDepartments()) {
            int size = this.selecteddepartments.size();
            return size > 1 ? size + " departments selected" : "1 department selected";
        }

        return "Delete";
    }

    public void deleteSelectedDepartments() {
        this.departments.removeAll(this.selecteddepartments);
        for (Department department : this.selecteddepartments) {
            departmentDao.delete(department);
        }
        this.selecteddepartments = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("departments Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-departments");
        PrimeFaces.current().executeScript("PF('dtdepartments').clearFilters()");
    }

    public void saveDepartment() {
        if (this.selecteddepartment.getId() == 0) {
            departmentDao.add(this.selecteddepartment);
            this.departments.add(this.selecteddepartment);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("department Added"));
        } else {
            departmentDao.update(this.selecteddepartment);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("department Updated"));
        }

        PrimeFaces.current().executeScript("PF('managedepartmentDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-departments");
        
    }

    public void deletedepartment() {
        departmentDao.delete(this.selecteddepartment);
        this.departments.remove(this.selecteddepartment);
        this.selecteddepartments.remove(this.selecteddepartment);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("department Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-departments");
        this.selecteddepartment = null;
    }

    public boolean hasnotSelecteddepartments() {
        return !hasSelectedDepartments();
    }
}
