package com.pi.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import javax.faces.bean.ViewScoped;
import com.pi.dao.CoursesGroupDao;
import com.pi.entities.CoursesGroup;

@ManagedBean(name = "coursesGroupMB")
@Named(value = "departmentMB")
@ViewScoped

public class CoursesGroupMB implements Serializable {
    private CoursesGroup coursesGroup = new CoursesGroup();
    private CoursesGroup selectedCoursesGroup;
    private List<CoursesGroup> coursesGroups;
    private List<CoursesGroup> selectedCoursesGroups;
    CoursesGroupDao coursesGroupDao = new CoursesGroupDao();

    public List<CoursesGroup> getSelectedCoursesGroups() {
        return selectedCoursesGroups;
    }

    public void setSelectedCoursesGroups(List<CoursesGroup> selectedCoursesGroups) {
        this.selectedCoursesGroups = selectedCoursesGroups;
    }

    public void initDataTable() {
        coursesGroups = coursesGroupDao.getAllCoursesGroups();
    }

    // getters and setters

    public CoursesGroup getCoursesGroup() {
        return coursesGroup;
    }

    public void setCoursesGroup(CoursesGroup coursesGroup) {
        this.coursesGroup = coursesGroup;
    }

    public CoursesGroup getSelectedCoursesGroup() {
        return selectedCoursesGroup;
    }

    public void setSelectedCoursesGroup(CoursesGroup selectedCoursesGroup) {
        this.selectedCoursesGroup = selectedCoursesGroup;
    }

    public List<CoursesGroup> getCoursesGroups() {
        return coursesGroups;
    }

    public void setCoursesGroups(List<CoursesGroup> coursesGroups) {
        this.coursesGroups = coursesGroups;
    }

    public void openNew() {
        this.selectedCoursesGroup = new CoursesGroup();
    }

    public boolean hasSelectedCoursesGroup() {
        return this.selectedCoursesGroups != null;
    }

    public boolean hasSelectedCoursesGroups() {
        return this.selectedCoursesGroups != null && !this.selectedCoursesGroups.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedCoursesGroups()) {
            int size = this.selectedCoursesGroups.size();
            return size > 1 ? size + " courses groups selected" : "1 courses group selected";
        }

        return "Delete";
    }

    public void deleteSelectedCoursesGroups() {
        this.coursesGroups.removeAll(this.selectedCoursesGroups);
        for (CoursesGroup coursesGroup : this.selectedCoursesGroups) {
            this.coursesGroupDao.delete(coursesGroup);
        }
        this.selectedCoursesGroups = null;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Courses groups removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-courses");
        PrimeFaces.current().executeScript("PF('dtCoursesGroups').clearFilters()");
    }

    public void saveCoursesGroup() {
        System.out.println(selectedCoursesGroup.toString());
        if (this.selectedCoursesGroup.getId() == null) {
            coursesGroupDao.add(this.selectedCoursesGroup);
            this.coursesGroups.add(this.selectedCoursesGroup);
            this.selectedCoursesGroup = null;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Courses group added"));

        } else {
            coursesGroupDao.update(this.selectedCoursesGroup);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Courses group updated"));
        }
        PrimeFaces.current().executeScript("PF('manageCoursesGroupDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-courses");
    }

    public void deleteSelectedCoursesGroup() {
        System.out.println("Hello");
        this.coursesGroups.remove(this.selectedCoursesGroup);
        this.coursesGroupDao.delete(this.selectedCoursesGroup);
        // this.selectedCoursesGroups.remove(this.selectedCoursesGroup);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Courses group removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-courses");
        this.selectedCoursesGroup = null;

    }

    public boolean hasnotSelectedCoursesGroups() {
        return !hasSelectedCoursesGroups();
    }
}
