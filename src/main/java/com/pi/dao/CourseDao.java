package com.pi.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.pi.entities.Course;
import com.pi.utils.JPAutil;

public class CourseDao {
    private EntityManager entityManager = JPAutil.getEntityManager("SchoolManagement");

    public void add(Course course) {
        entityManager.getTransaction().begin();
        entityManager.persist(course);
        entityManager.getTransaction().commit();
    }

    public void update(Course course) {
        entityManager.getTransaction().begin();
        entityManager.merge(course);
        entityManager.getTransaction().commit();
    }

    public void delete(Course course) {
        entityManager.getTransaction().begin();
        course = entityManager.merge(course);
        entityManager.remove(course);
        entityManager.getTransaction().commit();
    }

    // get course by id
    public Course getCourseById(int id) {
        return entityManager.find(Course.class, (long)id);
    }

    // get all courses
    public List<Course> getAllCourses() {
        return entityManager.createQuery("select c from Course c").getResultList();
    }

    // main method get all courses
    public static void main(String[] args) {
        CourseDao courseDao = new CourseDao();
        List<Course> courses = courseDao.getAllCourses();
        for (Course course : courses) {
            System.out.println(course.toString());
        }

        // get course that has id = 4 and print it
        Course course = courseDao.getCourseById(4);
        System.out.println(course.toString());
    }

}
