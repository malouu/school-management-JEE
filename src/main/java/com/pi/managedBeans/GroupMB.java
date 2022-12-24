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
    private StudentsGroup selectedGroup = new StudentsGroup();
    private List<StudentsGroup> StudentsGroups;
    private List<StudentsGroup> selectedStudentsGroups;
    @Inject
    
    GroupDao groupDao = new GroupDao();

    public String add() {
        groupDao.add(studentsGroup);
        return "groups.xhtml?faces-redirect=true";
    }

    public String update() {
        groupDao.update(selectedGroup);
        return "groups.xhtml?faces-redirect=true";
    }

    public String delete() {
        groupDao.delete(selectedGroup);
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
            this.selectedGroup = studentsGroup;
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
        return selectedGroup;
    }

    public void setSelectedStudentsGroup(StudentsGroup selectedStudentsGroup) {
        this.selectedGroup = selectedStudentsGroup;
    }

    public List<StudentsGroup> getSelectedStudentsGroups() {
        return selectedStudentsGroups;
    }

    public void setSelectedStudentsGroups(List<StudentsGroup> selectedStudentsGroups) {
        this.selectedStudentsGroups = selectedStudentsGroups;
    }

    public void openNew() {
    	this.selectedGroup= new StudentsGroup();
    }

    

    public boolean hasSelectedStudentsGroup() {
        return this.selectedGroup != null;
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
        if (this.selectedGroup.getId() == 0) {
           groupDao.add(this.selectedGroup);
            this.StudentsGroups.add(this.selectedGroup);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("StudentsGroup Added"));
        } else {
            groupDao.update(this.selectedGroup);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("StudentsGroup Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageStudentsGroupDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-StudentsGroups");
        
    }

    public void deleteStudentsGroup() {
       groupDao.delete(this.selectedGroup);
        this.StudentsGroups.remove(this.selectedGroup);
        this.selectedStudentsGroups.remove(this.selectedGroup);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("StudentsGroup Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-StudentsGroups");
        this.selectedGroup = null;
    }

    public boolean hasnotSelectedStudentsGroups() {
        return !hasSelectedStudentsGroups();
    }


}
