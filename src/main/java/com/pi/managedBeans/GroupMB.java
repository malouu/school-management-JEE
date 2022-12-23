package com.pi.managedBeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;

import com.pi.dao.GroupDao;
import com.pi.dao.StudentDao;
import com.pi.entities.StudentsGroup;

@ManagedBean(name = "groupMB")
@ViewScoped

public class GroupMB {
    private StudentsGroup studentsGroup = new StudentsGroup();
    private StudentsGroup selectedGroup = new StudentsGroup();
    private List<StudentsGroup> studentsGroups;
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
        studentsGroups = groupDao.getAllGroups();
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

    public StudentsGroup getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(StudentsGroup selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public List<StudentsGroup> getGroups() {
        return studentsGroups;
    }

}
