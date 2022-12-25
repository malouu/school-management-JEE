package com.pi.services;

import com.pi.dao.GroupDao;
import com.pi.entities.StudentsGroup;

public class GroupService {
    private final GroupDao gpDao;

    public GroupService(GroupDao gpDao) {
        this.gpDao = gpDao;
    }
    //add inexistent group
    public void addGroup(StudentsGroup gp) {
        if(gpDao.getGroupById(gp.getId()) == null) {
            gpDao.add(gp);
        }
        throw new RuntimeException("Group already exists, The ID was found in the database");
        
    }
    //update group
    public void updateGroup(StudentsGroup gp) {
        if(gpDao.getGroupById(gp.getId()) != null) {
            gpDao.update(gp);
        }
        throw new RuntimeException("Group does not exist, The ID was not found in the database");
    }
    //delete group
    public void deleteGroup(StudentsGroup gp) {
        if(gpDao.getGroupById(gp.getId()) != null) {
            gpDao.delete(gp);
        }
        throw new RuntimeException("Group does not exist, The ID was not found in the database");
    }



}
