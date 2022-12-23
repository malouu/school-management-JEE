package com.pi.managedBeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import com.pi.dao.StudentDao;
import com.pi.entities.Student;

@ManagedBean(name = "studentMB")
@ViewScoped

public class StudentMB {
    private Student student = new Student();
    private Student selectedStudent = new Student();
    private List<Student> students;
    StudentDao studentDao = new StudentDao();

    
    public String add() {
        studentDao.add(student);
        return "student.xhtml?faces-redirect=true";
    }

    public String update() {
        studentDao.update(selectedStudent);
        return "student.xhtml?faces-redirect=true";
    }

    public String delete() {
        studentDao.delete(selectedStudent);
        return "student.xhtml?faces-redirect=true";
    }

    public void initDataTable() {
        students = studentDao.getAllStudents();
    }

    public void initForm() {
        int SubscrpNumber;
        SubscrpNumber = Integer.parseInt(
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("code"));
        Student student = new Student();
        student = studentDao.getStudentById(SubscrpNumber);
        System.out.print(student.toString());
        if (student != null) {
            this.selectedStudent = student;
        }
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

}
