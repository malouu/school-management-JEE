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

@ManagedBean(name = "gradesMB")
@Named(value = "gradesMB")
@ViewScoped

public class GradesMB implements Serializable {
    private List<Student> students;
    private Course course = new Course();

    @Inject
    GradeDao gradeDao = new GradeDao();
    @Inject
    StudentDao studentDao = new StudentDao();

    public void initDataTable() {
        students = studentDao.getAllStudents();
    }

    public List<Student> getStudents() {
        return students;
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
