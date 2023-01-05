package com.pi.dao;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.pi.entities.Grade;
import com.pi.utils.JPAutil;

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

}
