package com.pi.managedBeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;

import com.pi.dao.GroupDao;
import com.pi.dao.StudentDao;
import com.pi.entities.Group;

@ManagedBean(name = "groupMB")
@ViewScoped

public class GroupMB {
    private Group group = new Group();
    private Group selectedGroup = new Group();
    private List<Group> groups;
    GroupDao groupDao = new GroupDao();

    public String add() {
        groupDao.add(group);
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
        groups = groupDao.getAllGroups();
    }

    public void initForm() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

        Group group = new Group();
        group = groupDao.getGroupById(Integer.parseInt(id));
        if (group != null) {
            this.selectedGroup = group;
        }
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Group selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public List<Group> getGroups() {
        return groups;
    }

}
