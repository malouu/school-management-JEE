package com.pi.dao;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.pi.entities.Course;
import com.pi.entities.Grade;
import com.pi.entities.GradeType;
import com.pi.entities.Student;
import com.pi.utils.JPAutil;

@Named
@SessionScoped
public class GradeDao {

    private EntityManager entityManager = JPAutil.getEntityManager("SchoolManagement");

    public void add(Grade grade) {
        entityManager.getTransaction().begin();
        entityManager.persist(grade);
        entityManager.getTransaction().commit();
    }

    public void update(Grade grade) {
        entityManager.getTransaction().begin();
        entityManager.merge(grade);
        entityManager.getTransaction().commit();
    }

    public void delete(Grade grade) {
        entityManager.getTransaction().begin();
        grade = entityManager.merge(grade);
        entityManager.remove(grade);
        entityManager.getTransaction().commit();
    }

    // get grade by id
    public Grade getGradeById(int id) {
        return entityManager.find(Grade.class, id);
    }

    // get all grades
    public List<Grade> getAllGrades() {
        return entityManager.createQuery("select g from Grade g").getResultList();
    }

    public List<Grade> getStudentGrades(Student student) {
        return entityManager.createQuery("select g from Grade g where g.student = :student")
                .setParameter("student", student)
                .getResultList();
    }

    public List<Grade> getStudentCourseGrades(Student student, Course course) {
        return entityManager.createQuery("select g from Grade g where g.student = :student and g.course = :course")
                .setParameter("student", student)
                .setParameter("course", course)
                .getResultList();
    }

    public List<Grade> getCourseGrades(Course course) {
        return entityManager.createQuery("select g from Grade g where g.course = :course")
                .setParameter("course", course)
                .getResultList();
    }

    public List<Grade> getGradeTypeGrades(GradeType gradeType) {
        return entityManager.createQuery("select g from Grade g where g.gradeType = :gradeType")
                .setParameter("gradeType", gradeType)
                .getResultList();
    }

    // delete all grades of a student
    public void deleteStudentGrades(Student student) {
        List<Grade> grades = getStudentGrades(student);
        for (Grade grade : grades) {
            delete(grade);
        }
    }

    // main method for testing adding a new grade
    public static void main(String[] args) {
        GradeDao gradeDao = new GradeDao();
        Grade grade = new Grade();
        StudentDao studentDao = new StudentDao();
        CourseDao courseDao = new CourseDao();
        GradeTypeDao gradeTypeDao = new GradeTypeDao();

        Student student = studentDao.getStudentById(1);
        System.out.println(student.toString());
        Course course = courseDao.getCourseById(4);
        System.out.println(course.toString());
        GradeType gradeType = gradeTypeDao.getGradeTypeById(1);

        grade.setStudent(student);
        grade.setCourse(course);
        grade.setGradeType(gradeType);
        grade.setValue(10);
        System.out.println(grade.toString());

        gradeDao.add(grade);
    }
}
