package com.pi.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;

import javax.faces.bean.ViewScoped;

import com.pi.dao.CourseDao;
import com.pi.dao.GradeDao;
import com.pi.dao.GradeTypeDao;
import com.pi.dao.GroupDao;
import com.pi.dao.StudentDao;
import com.pi.entities.Student;
import com.pi.entities.StudentsGroup;
import com.pi.services.CourseService;
import com.pi.services.StudentService;
import com.pi.entities.Grade;
import com.pi.entities.Course;
import com.pi.entities.GradeType;

@ManagedBean(name = "pvMB")
@Named(value = "pvMB")
@ViewScoped
public class PvMB implements Serializable {
    private List<Student> students;
    private List<Course> courses;
    private List<ColumnModel> columns = new ArrayList<ColumnModel>();
    private StudentsGroup group;

    private StudentService studentService = new StudentService();

    GradeDao gradeDao = new GradeDao();

    StudentDao studentDao = new StudentDao();

    GradeTypeDao gradeTypeDao = new GradeTypeDao();

    CourseDao courseDao = new CourseDao();

    GroupDao gourpDao = new GroupDao();

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public void initDataTable() {
        // get group ID from request
        int groupId;
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("groupId") != null) {
            groupId = Integer.parseInt(
                    FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("groupId"));
            group = gourpDao.getGroupById(groupId);

        } else {
            students = studentDao.getAllStudents();
        }
        // courses = group.getCourses();
        this.studentService = new StudentService();
        courses = courseDao.getAllCourses();
        studentService.setCourse(null);
        populateColumns();
    }

    public void populateColumns() {
        // only populate the columns once
        if (columns.isEmpty()) {
            for (Course course : courses) {
                columns.add(new ColumnModel(course.getName(), course));
            }
        }
    }

    public String createLabel(Student student) {

        if (studentService.getStudentAverage(student) >= 10) {
            return "SUCCESS";
        } else {
            return "FAILURE";
        }
    }

    public float CourseAverage(Student student, Course course) {
        studentService.setStudent(student);
        studentService.setCourse(course);
        return studentService.getStudentCourseAveragec(student, course);
    }

    public float Average(Student student) {
        studentService.setStudent(student);
        return studentService.getStudentAverage(student);
    }

    public String FinalResult(Student student) {
        studentService.setStudent(student);
        return studentService.getStudentFinalResult(student);
    }

    static public class ColumnModel implements Serializable {
        private String header;
        private Course property;

        public ColumnModel(String header, Course property) {
            this.header = header;
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public Course getProperty() {
            return property;
        }
    }

}
