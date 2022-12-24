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

import com.pi.dao.GroupDao;
import com.pi.entities.StudentsGroup;

@ManagedBean(name = "groupMB")
@Named(value = "groupMB")
@ViewScoped

public class GroupMB implements Serializable {
   
	private static final long serialVersionUID = 1L;
	private StudentsGroup studentsGroup = new StudentsGroup();
    private StudentsGroup selectedStudentsGroup = new StudentsGroup();
    private List<StudentsGroup> StudentsGroups;
    private List<StudentsGroup> selectedStudentsGroups;
    @Inject
    
    GroupDao groupDao = new GroupDao();

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

    public void initDataTable() {
    	StudentsGroups = groupDao.getAllGroups();
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
        return StudentsGroups;
    }

    public void setStudentsGroups(List<StudentsGroup> StudentsGroups) {
        this.StudentsGroups = StudentsGroups;
    }

    public StudentsGroup getSelectedStudentsGroup() {
        return selectedStudentsGroup;
    }

    public void setSelectedStudentsGroup(StudentsGroup selectedStudentsGroup) {
        this.selectedStudentsGroup = selectedStudentsGroup;
    }

    public List<StudentsGroup> getSelectedStudentsGroups() {
        return selectedStudentsGroups;
    }

    public void setSelectedStudentsGroups(List<StudentsGroup> selectedStudentsGroups) {
        this.selectedStudentsGroups = selectedStudentsGroups;
    }

    public void openNew() {
    	this.selectedStudentsGroup= new StudentsGroup();
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
            return size > 1 ? size + " StudentsGroups selected" : "1 StudentsGroup selected";
        }

        return "Delete";
    }

    public void deleteSelectedStudentsGroups() {
        this.StudentsGroups.removeAll(this.selectedStudentsGroups);
        for (StudentsGroup StudentsGroup : this.selectedStudentsGroups) {
            groupDao.delete(StudentsGroup);
        }
        this.selectedStudentsGroups = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("StudentsGroups Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-StudentsGroups");
        PrimeFaces.current().executeScript("PF('dtStudentsGroups').clearFilters()");
    }

    public void saveStudentsGroup() {
        if (this.selectedStudentsGroup.getId() == 0) {
           groupDao.add(this.selectedStudentsGroup);
            this.StudentsGroups.add(this.selectedStudentsGroup);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("StudentsGroup Added"));
        } else {
            groupDao.update(this.selectedStudentsGroup);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("StudentsGroup Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageStudentsGroupDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-StudentsGroups");
        
    }

    public void deleteStudentsGroup() {
       groupDao.delete(this.selectedStudentsGroup);
        this.StudentsGroups.remove(this.selectedStudentsGroup);
        this.selectedStudentsGroups.remove(this.selectedStudentsGroup);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("StudentsGroup Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-StudentsGroups");
        this.selectedStudentsGroup = null;
    }

    public boolean hasnotSelectedStudentsGroups() {
        return !hasSelectedStudentsGroups();
    }


}
