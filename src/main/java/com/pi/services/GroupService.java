package com.pi.services;

import java.util.List;
import java.util.stream.Collectors;

import com.pi.dao.GroupDao;
import com.pi.entities.Course;
import com.pi.entities.CoursesGroup;
import com.pi.entities.StudentsGroup;

public class GroupService {
    private final GroupDao gpDao;
    private StudentsGroup gp;

    public GroupService(GroupDao gpDao) {
        this.gpDao = gpDao;
    }

    // add inexistent group
    public void addGroup(StudentsGroup gp) {
        if (gpDao.getGroupById(gp.getId()) == null) {
            gpDao.add(gp);
        }
        throw new RuntimeException("Group already exists, The ID was found in the database");

    }

    // update group
    public void updateGroup(StudentsGroup gp) {
        if (gpDao.getGroupById(gp.getId()) != null) {
            gpDao.update(gp);
        }
        throw new RuntimeException("Group does not exist, The ID was not found in the database");
    }

    // delete group
    public void deleteGroup(StudentsGroup gp) {
        if (gpDao.getGroupById(gp.getId()) != null) {
            gpDao.delete(gp);
        }
        throw new RuntimeException("Group does not exist, The ID was not found in the database");
    }

    public StudentsGroup getGroup() {
        return gp;
    }

    public void setGroup(StudentsGroup gp) {
        this.gp = gp;
    }

    public List<Course> getCourses() {
        return gp.getCoursesGroups().stream().map(cg -> cg.getCourses()).flatMap(c -> c.stream())
                .collect(Collectors.toList());
    }

}
