package com.pi.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;

import javax.faces.bean.ViewScoped;

import com.pi.dao.CourseDao;
import com.pi.dao.GradeDao;
import com.pi.dao.GradeTypeDao;
import com.pi.dao.StudentDao;
import com.pi.entities.Student;
import com.pi.services.StudentService;
import com.pi.entities.Grade;
import com.pi.entities.Course;
import com.pi.entities.GradeType;

@SuppressWarnings({ "serial", "serial" })
@ManagedBean(name = "gradesMB")
@Named(value = "gradesMB")
@ViewScoped

public class GradesMB implements Serializable {
    private List<Student> students;
    private Course course = null;
    private List<GradeType> gradeTypes = new ArrayList<GradeType>();
    private List<ColumnModel> columns = new ArrayList<ColumnModel>();
    private StudentService studentService = new StudentService();

    @Inject
    GradeDao gradeDao = new GradeDao();
    @Inject
    StudentDao studentDao = new StudentDao();
    @Inject
    GradeTypeDao gradeTypeDao = new GradeTypeDao();
    @Inject
    CourseDao courseDao = new CourseDao();

    public void initDataTable() {
        students = studentDao.getAllStudents();
        // print the list of students

        // only do the following once
        gradeTypes = gradeTypeDao.getAllGradeTypes();
        if (gradeTypes.isEmpty()) {
            gradeTypeDao.add(new GradeType("Exam", (float) 0.7));
            gradeTypeDao.add(new GradeType("Project", (float) 0.1));
            gradeTypeDao.add(new GradeType("DS", (float) 0.2));
            gradeTypes = gradeTypeDao.getAllGradeTypes();
        }

        if (course == null) {
            course = new Course();
            course.setName("Java");
            course.setGradeTypes(gradeTypes);
            course.setCoef(2f);
            courseDao.add(course);
            studentService.setCourse(course);
        }

        // only populate the columns once using populateColumns()
        populateColumns();
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

    public StudentService getStudentService() {
        return studentService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        // get the grade
        // Grade grade = (Grade) event.getSource();
        // print grade info
        // System.out.println(
        // "Grade: " + grade.getId() + " " + grade.getGradeType().getName() + " " +
        // grade.getValue());

        FacesContext context = FacesContext.getCurrentInstance();
        Student student = context.getApplication().evaluateExpressionGet(context, "#{student}",
                Student.class);
        GradeType gradeType = context.getApplication().evaluateExpressionGet(context,
                "#{column.property}", GradeType.class);

        // print grade type info
        System.out.println("GradeType: " + gradeType.getName());

        // loop through the grades of the student and find the grade that matches the
        // grade type
        Grade grade = null;
        for (Grade g : student.getGrades()) {

            if (g.getGradeType().equals(gradeType)) {
                grade = g;
                break;
            }
        }
        System.out
                .println("Grade: " + grade.getGradeType().getName() + " " + grade.getValue() + " id:" + grade.getId());
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
                    "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            // update the grade value
            gradeDao.update(grade);
        }
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public void populateColumns() {
        // only populate the columns once
        if (columns.isEmpty()) {
            for (GradeType gradeType : gradeTypes) {
                columns.add(new ColumnModel(gradeType.getName(), gradeType));
            }
        }
    }

    static public class ColumnModel implements Serializable {
        private String header;
        private GradeType property;

        public ColumnModel(String header, GradeType property) {
            this.header = header;
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public GradeType getProperty() {
            return property;
        }
    }
}
