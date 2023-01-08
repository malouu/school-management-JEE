package com.pi.dao;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.pi.entities.Course;
import com.pi.entities.Grade;
import com.pi.entities.GradeType;
import com.pi.entities.Student;
import com.pi.utils.JPAutil;

@Named
@ApplicationScoped
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
