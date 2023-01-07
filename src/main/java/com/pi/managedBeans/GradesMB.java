package com.pi.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;

import javax.faces.bean.ViewScoped;

import com.pi.dao.GradeDao;
import com.pi.dao.StudentDao;
import com.pi.entities.Student;
import com.pi.entities.Grade;
import com.pi.entities.Course;
import com.pi.entities.GradeType;

@ManagedBean(name = "gradesMB")
@Named(value = "gradesMB")
@ViewScoped

public class GradesMB implements Serializable {
    private List<Student> students;
    private Course course = new Course();
    private List<GradeType> gradeTypes;

    @Inject
    GradeDao gradeDao = new GradeDao();
    @Inject
    StudentDao studentDao = new StudentDao();

    public void initDataTable() {
        students = studentDao.getAllStudents();
        gradeTypes.add(new GradeType("Midterm", 0.2));
        gradeTypes.add(new GradeType("Project", 0.1));
        gradeTypes.add(new GradeType("Final", 0.7));
        course.setGradeTypes(gradeTypes);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<GradeType> getGradeTypes() {
        return gradeTypes;
    }

    public void setGradeTypes(List<GradeType> gradeTypes) {
        this.gradeTypes = gradeTypes;
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
                    "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
