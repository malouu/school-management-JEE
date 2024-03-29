package com.pi.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import javax.faces.bean.ViewScoped;
import com.pi.dao.StudentDao;
import com.pi.entities.Student;

@ManagedBean(name = "studentMB")
@Named(value = "studentMB")
@ViewScoped

public class StudentMB implements Serializable {
    private Student student = new Student();
    private Student selectedStudent = new Student();
    private List<Student> students;
    private List<Student> selectedStudents;
    private List<Student> filteredStudents;

    @Inject
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

    public List<Student> getSelectedStudents() {
        return selectedStudents;
    }

    public void setSelectedStudents(List<Student> selectedStudents) {
        this.selectedStudents = selectedStudents;
    }

    public void openNew() {
        this.selectedStudent = new Student();
    }

    public boolean hasSelectedStudent() {
        return this.selectedStudent != null;
    }

    public boolean hasSelectedStudents() {
        return this.selectedStudents != null && !this.selectedStudents.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedStudents()) {
            int size = this.selectedStudents.size();
            return size > 1 ? size + " students selected" : "1 student selected";
        }

        return "Delete";
    }

    public void deleteSelectedStudents() {
        this.students.removeAll(this.selectedStudents);
        for (Student student : this.selectedStudents) {
            studentDao.delete(student);
        }
        this.selectedStudents = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Students Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-students");
        PrimeFaces.current().executeScript("PF('dtStudents').clearFilters()");
    }

    public void saveStudent() {
    	
        if (this.selectedStudent.getSubscription_number() == 0) {
            studentDao.add(this.selectedStudent);
            this.students.add(this.selectedStudent);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Student Added"));
        } else {
            studentDao.update(this.selectedStudent);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Student Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageStudentDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-students");

    }

    public void deleteStudent() {
        studentDao.delete(this.selectedStudent);
        this.students.remove(this.selectedStudent);
        this.selectedStudents.remove(this.selectedStudent);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Student Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-students");
        this.selectedStudent = null;
    }

    public boolean hasnotSelectedStudents() {
        return !hasSelectedStudents();
    }
    public List<Student> getFilteredStudents() {
        System.out.println("getFilteredStudents"+filteredStudents);
        return filteredStudents;
    }
    public void setFilteredStudents(List<Student> filteredStudents) {
    	System.out.println("setFilteredStudents"+filteredStudents);
        this.filteredStudents = filteredStudents;
    }

    public boolean globalFilterFunction(Object value, Object filter) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        System.out.println("filterText: " + filterText);
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        int filterInt = getInteger(filterText);

        Student student = (Student) value;
        return student.getSubscription_number() == filterInt
                || student.getName().toLowerCase().contains(filterText)
                || student.getSurname().toLowerCase().contains(filterText)
                || student.getEmail().toLowerCase().contains(filterText)
                || student.getPhone_number().toLowerCase().contains(filterText);
    }
    private int getInteger(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }
}
// parcourir une liste et afficher ses elements en java
// Path: StudentDao.java
