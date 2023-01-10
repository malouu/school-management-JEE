package com.pi.services;

import java.util.List;
import java.util.stream.Collectors;

import com.pi.dao.CourseDao;
import com.pi.entities.Course;
import com.pi.entities.StudentsGroup;

public class CourseService {

    private CourseDao courseDao = new CourseDao();
    private StudentsGroup group;

    public List<Course> getAllGroupCourses() {
        // group has a list of CoursesGroups. Each CourseGroup has a list of courses.
        // Flatten the list of CoursesGroups into a list of courses
        return group.getCoursesGroups().stream()
                .flatMap(cg -> cg.getCourses().stream())
                .collect(Collectors.toList());

    }

}
