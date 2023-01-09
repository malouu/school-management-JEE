package com.pi.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.pi.services.CourseService;
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
    private String courseString = "Maleeeeeek";
    private List<GradeType> gradeTypes = new ArrayList<GradeType>();
    private List<ColumnModel> columns = new ArrayList<ColumnModel>();
    private StudentService studentService = new StudentService();
    private Map<String, Course> courses = new HashMap<String, Course>();
    private List<Course> coursesList = new ArrayList<Course>();
    private int number;
    private String test;
    private Map<String, String> testMap = new HashMap<String, String>();
    private Course selectedCourse;
    private long selectedCourseId;

    public long getSelectedCourseId() {
        return selectedCourseId;
    }

    public void setSelectedCourseId(long selectedCourseId) {
        this.selectedCourseId = selectedCourseId;
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public List<Course> getCoursesList() {
        System.out.println("?????????????????? courses list size  " + coursesList.size());
        return coursesList;
    }

    public void setCoursesList(List<Course> coursesList) {
        System.out.println("?????????????????? set courses list size  " + coursesList.size());
        this.coursesList = coursesList;
    }

    public Map<String, String> getTestMap() {
        return testMap;
    }

    public void setTestMap(Map<String, String> testMap) {
        this.testMap = testMap;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        System.out.println("?????????????????? set test: " + test);
        this.test = test;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String text5;

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getText4() {
        return text4;
    }

    public void setText4(String text4) {
        this.text4 = text4;
    }

    public String getText5() {
        return text5;
    }

    public void setText5(String text5) {
        this.text5 = text5;
    }

    public String getCourseString() {
        return courseString;
    }

    public void setCourseString(String courseString) {

        this.courseString = courseString;
    }

    public Map<String, Course> getCourses() {
        return courses;
    }

    public void setCourses(Map<String, Course> courses) {
        this.courses = courses;
    }

    private CourseService courseService = new CourseService();

    @Inject
    GradeDao gradeDao = new GradeDao();
    @Inject
    StudentDao studentDao = new StudentDao();
    @Inject
    GradeTypeDao gradeTypeDao = new GradeTypeDao();
    @Inject
    CourseDao courseDao = new CourseDao();

    public void initDataTable() {

        testMap.put("Malek", "1");
        testMap.put("Malek2", "2");
        testMap.put("Malek3", "3");

        students = studentDao.getAllStudents();
        int courseId;
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("courseId") != null) {
            courseId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                    .get("courseId"));
            System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            System.out.println("courseId: " + courseId);
        } else {
            courseId = 1;
        }
        // print the list of students

        // only do the following once
        gradeTypes = gradeTypeDao.getAllGradeTypes();

        // for each course add to the hash its name and id
        setCoursesList(courseDao.getAllCourses());
        for (Course coursee : courseDao.getAllCourses()) {
            courses.put(coursee.getName(), coursee);
        }
        // CourseService.setGroup(group);
        // setCourses(courseService.getAllGroupCourses());
        course = courseDao.getCourseById(courseId);
        if (gradeTypes.isEmpty()) {
            gradeTypeDao.add(new GradeType("Exam", (float) 0.7));
            gradeTypeDao.add(new GradeType("Project", (float) 0.1));
            gradeTypeDao.add(new GradeType("DS", (float) 0.2));
            gradeTypes = gradeTypeDao.getAllGradeTypes();
        }

        // if (course == null) {
        // // System.out.println("course is null");
        // course = new Course();
        // course.setName("Java");
        // course.setGradeTypes(gradeTypes);
        // course.setCoef(2f);
        // courseDao.add(course);

        // }
        // if (course != null) {
        // System.out.println("course is not null");
        // // System.out.println("course name: " + course.getName());
        // // System.out.println("course id: " + course.getId());
        // }
        studentService.setCourse(course);

        // only populate the columns once using populateColumns()
        populateColumns();
    }

    public void onCourseChange() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if (course != null) {
            System.out.println("course name: " + course.getName());
            studentService.setCourse(course);
        }
    }

    public void displayCourse() {
        FacesMessage msg;

        if (course != null) {
            msg = new FacesMessage("Selected", course.getName());
        } else
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "Course is not selected.");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void showCourse() {
        System.out.println("%%%%%%%% " + this.course.toString());
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Course getCourse() {
        System.out.println("get course name: " + course.getName());
        return course;
    }

    public void setCourse(Course course) {
        System.out.println("set course name: " + course.getName());
        this.course = course;
        System.out.println("-------set course  " + this.course.toString());
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

        // convert the newValue to a float
        float newValueFloat = Float.parseFloat(newValue.toString());
        // convert the oldValue to a float
        float oldValueFloat = Float.parseFloat(oldValue.toString());

        // System.out.println("oldValue: " + oldValueFloat);
        // System.out.println("newValue: " + newValueFloat);

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
        // System.out.println("GradeType: " + gradeType.getName());

        // loop through the grades of the student and find the grade that matches the
        // grade type
        Grade grade = null;
        for (Grade g : student.getGrades()) {

            if (g.getGradeType().equals(gradeType)) {
                grade = g;
                break;
            }
        }
        // System.out
        // .println("Grade: " + grade.getGradeType().getName() + " " + grade.getValue()
        // + " id:" + grade.getId());
        if (newValue != null && !newValue.equals(oldValue)) {
            // if the newValue is greater than 20 or less than 0, show an error message
            if (newValueFloat > 20 || newValueFloat < 0) {
                // System.out.println("Error: Grade must be between 0 and 20");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                        "Grade must be between 0 and 20");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                // preserve the old value
                grade.setValue((int) oldValue);
                // clear the input field
            } else {
                // update the grade value
                gradeDao.update(grade);
                // print the new grade value
                // System.out.println("Grade: " + grade.getGradeType().getName() + " " +
                // grade.getValue());
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Grade Updated",
                        "Old: " + oldValue + ", New:" + newValue);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

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

    public void increment() {
        number++;
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
