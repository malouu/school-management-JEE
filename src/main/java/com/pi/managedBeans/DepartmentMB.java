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
    private Department selectedDepartment = new Department();
    private List<Department> departments;
    private List<Department> selectedDepartments;

    @Inject
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
        int id;
        id = Integer.parseInt(
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("code"));
        Department department = new Department();
        department = departmentDao.getDepartmentById(id);
        System.out.print(department.toString());
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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public Department getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(Department selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public List<Department> getSelectedDepartments() {
        return selectedDepartments;
    }

    public void setSelectedDepartments(List<Department> selectedDepartments) {
        this.selectedDepartments = selectedDepartments;
    }

    public void openNew() {
    	this.selectedDepartment= new Department();
    }

    

    public boolean hasSelectedDepartment() {
        return this.selectedDepartment != null;
    }

    public boolean hasSelectedDepartments() {
        return this.selectedDepartments != null && !this.selectedDepartments.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedDepartments()) {
            int size = this.selectedDepartments.size();
            return size > 1 ? size + " departments selected" : "1 department selected";
        }

        return "Delete";
    }

    public void deleteSelectedDepartments() {
        this.departments.removeAll(this.selectedDepartments);
        for (Department department : this.selectedDepartments) {
            departmentDao.delete(department);
        }
        this.selectedDepartments = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Departments Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-departments");
        PrimeFaces.current().executeScript("PF('dtDepartments').clearFilters()");
    }

    public void saveDepartment() {
    	System.out.println(selectedDepartment.toString());
        if (this.selectedDepartment.getId() == null) {
            departmentDao.add(this.selectedDepartment);
            this.departments.add(this.selectedDepartment);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Department Added"));
        } else {
            departmentDao.update(this.selectedDepartment);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Department Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageDepartmentDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-departments");
        System.out.println("khalil!!!!");
        
    }

    public void deleteDepartment() {
        departmentDao.delete(this.selectedDepartment);
        this.departments.remove(this.selectedDepartment);
        this.selectedDepartments.remove(this.selectedDepartment);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Department Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-departments");
        this.selectedDepartment = null;
    }

    public boolean hasnotSelectedDepartments() {
        return !hasSelectedDepartments();
    }
}
