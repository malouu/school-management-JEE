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
import com.pi.dao.GroupDao;
import com.pi.entities.Department;
import com.pi.entities.Student;
import com.pi.entities.StudentsGroup;

@ManagedBean(name = "groupMB")
@Named(value = "groupMB")
@ViewScoped

public class GroupMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private StudentsGroup studentsGroup = new StudentsGroup();
    private StudentsGroup selectedStudentsGroup = new StudentsGroup();
    private List<StudentsGroup> studentsGroups;
    private List<StudentsGroup> selectedStudentsGroups;
    @Inject

    GroupDao groupDao = new GroupDao();
    Department department =new Department();
    DepartmentDao deptDao = new DepartmentDao();

    public String add() {
        groupDao.add(studentsGroup);
        return "groups.xhtml?faces-redirect=true";
    }

    public String update() {
        groupDao.update(studentsGroup);
        return "groups.xhtml?faces-redirect=true";
    }

    public String delete() {
        groupDao.delete(studentsGroup);
        return "groups.xhtml?faces-redirect=true";
    }

    public List<StudentsGroup> initDataTable() {
        
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("code");
        department=deptDao.getDepartmentById(Long.parseLong(id));
        studentsGroups = groupDao.getStudentsByDepartmentId(Long.parseLong(id));
        return (studentsGroups);


    }

    public void initForm() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

        StudentsGroup studentsGroup = new StudentsGroup();
        studentsGroup = groupDao.getGroupById(Integer.parseInt(id));
        if (studentsGroup != null) {
            this.selectedStudentsGroup = studentsGroup;
        }
    }

    public StudentsGroup getGroup() {
        return studentsGroup;
    }

    public void setGroup(StudentsGroup studentsGroup) {
        this.studentsGroup = studentsGroup;
    }

    public List<StudentsGroup> getStudentsGroups() {
        return studentsGroups;
    }

    public void setStudentsGroups(List<StudentsGroup> StudentsGroups) {
        this.studentsGroups = StudentsGroups;
    }

    public StudentsGroup getSelectedStudentsGroup() {
        return selectedStudentsGroup;
    }

    public void setSelectedStudentsGroup(StudentsGroup selectedStudentsGroup) {
        this.selectedStudentsGroup = selectedStudentsGroup;
    }

    public List<StudentsGroup> getSelectedStudentsGroups() {
        System.out.println("Bye");
        return selectedStudentsGroups;
    }

    public void setSelectedStudentsGroups(List<StudentsGroup> selectedStudentsGroups) {
        this.selectedStudentsGroups = selectedStudentsGroups;
        System.out.println("hello" + selectedStudentsGroups);
    }

    public void openNew() {
        this.selectedStudentsGroup = new StudentsGroup();
    }

    public boolean hasSelectedStudentsGroup() {
        return this.selectedStudentsGroup != null;
    }

    public boolean hasSelectedStudentsGroups() {
        return this.selectedStudentsGroups != null && !this.selectedStudentsGroups.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedStudentsGroups()) {
            int size = this.selectedStudentsGroups.size();
            return size > 1 ? size + " Students Groups selected" : "1 Students Group selected";
        }

        return "Delete";
    }

    public void deleteSelectedStudentsGroups() {
        this.studentsGroups.removeAll(this.selectedStudentsGroups);
        for (StudentsGroup StudentsGroup : this.selectedStudentsGroups) {
            groupDao.delete(StudentsGroup);
        }
        this.selectedStudentsGroups = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("StudentsGroups Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-groups");
        PrimeFaces.current().executeScript("PF('dtGroups').clearFilters()");
    }

    public void saveStudentsGroup() {
        System.out.println("saveStudentsGroup");
        if (this.selectedStudentsGroup.getId() == 0) {
        	selectedStudentsGroup.setDepartment(department);
            groupDao.add(this.selectedStudentsGroup);
            this.studentsGroups.add(this.selectedStudentsGroup);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Students Group Added"));
        } else {
            groupDao.update(this.selectedStudentsGroup);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Students Group Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageStudentsGroupDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-groups");

    }

    public void deleteStudentsGroup() {
        groupDao.delete(this.selectedStudentsGroup);
        this.studentsGroups.remove(this.selectedStudentsGroup);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Students Group Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-groups");
        this.selectedStudentsGroup = null;
    }

    public boolean hasnotSelectedStudentsGroups() {
        System.out.println(selectedStudentsGroups);
        return !hasSelectedStudentsGroups();
    }
    
}
