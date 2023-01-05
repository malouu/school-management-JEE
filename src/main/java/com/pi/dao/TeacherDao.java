package com.pi.dao;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.pi.entities.Teacher;
import com.pi.utils.JPAutil;

@Named
@ApplicationScoped

public class TeacherDao {

    private EntityManager entityManager = JPAutil.getEntityManager("SchoolManagement");

    public void add(Teacher teacher) {
        entityManager.getTransaction().begin();
        entityManager.persist(teacher);
        entityManager.getTransaction().commit();
    }

    public void update(Teacher teacher) {
        entityManager.getTransaction().begin();
        entityManager.merge(teacher);
        entityManager.getTransaction().commit();
    }

    public void delete(Teacher teacher) {
        entityManager.getTransaction().begin();
        teacher = entityManager.merge(teacher);
        entityManager.remove(teacher);
        entityManager.getTransaction().commit();
    }

    // get teacher by id
    public Teacher getTeacherById(int id) {
        return entityManager.find(Teacher.class, id);
    }

    // get all teachers
    public List<Teacher> getAllTeachers() {
        return entityManager.createQuery("select c from Teacher c").getResultList();
    }

}
