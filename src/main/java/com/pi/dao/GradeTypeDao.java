package com.pi.dao;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.pi.entities.GradeType;
import com.pi.utils.JPAutil;
@Named
@ApplicationScoped
public class GradeTypeDao {

    private EntityManager entityManager = JPAutil.getEntityManager("SchoolManagement");

    public void add(GradeType gradeType) {
        entityManager.getTransaction().begin();
        entityManager.persist(gradeType);
        entityManager.getTransaction().commit();
    }

    public void update(GradeType gradeType) {
        entityManager.getTransaction().begin();
        entityManager.merge(gradeType);
        entityManager.getTransaction().commit();
    }

    public void delete(GradeType gradeType) {
        entityManager.getTransaction().begin();
        gradeType = entityManager.merge(gradeType);
        entityManager.remove(gradeType);
        entityManager.getTransaction().commit();
    }

    // get types by id
    public GradeType getGradeTypeById(int id) {
        return entityManager.find(GradeType.class, id);
    }

    // get all grade types
    public List<GradeType> getAllGradeTypes() {
        return entityManager.createQuery("select g from GradeType g").getResultList();
    }

}
