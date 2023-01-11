package com.pi.dao;

import java.util.List;

import javax.faces.bean.ApplicationScoped;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.pi.entities.CoursesGroup;
import com.pi.utils.JPAutil;

@Named
@ApplicationScoped
public class CoursesGroupDao {
    private EntityManager entityManager = JPAutil.getEntityManager("SchoolManagement");
    public void add(CoursesGroup coursesGroup) {
		entityManager.getTransaction().begin();
		entityManager.persist(coursesGroup);
		entityManager.getTransaction().commit();
	}

	public void update(CoursesGroup coursesGroup) {
		entityManager.getTransaction().begin();
		entityManager.merge(coursesGroup);
		entityManager.getTransaction().commit();
	}

	public void delete(CoursesGroup coursesGroup) {
		entityManager.getTransaction().begin();
		coursesGroup = entityManager.merge(coursesGroup);
		entityManager.remove(coursesGroup);
		entityManager.getTransaction().commit();
	}
    //get coursegroup by id
    public CoursesGroup getCoursesGroupById(int id) {
        return entityManager.find(CoursesGroup.class, id);
    }
    //get all coursesgroups
    public List<CoursesGroup> getAllCoursesGroups() {
        List<CoursesGroup> coursesGroups = entityManager.createQuery("select c from CoursesGroup c").getResultList();
        return coursesGroups;
    }
    //get coursesgroup by name
    public List<CoursesGroup> getCoursesGroupByName(String name) {
        List<CoursesGroup> coursesGroups = entityManager.createQuery("select c from CoursesGroup where c.name like :name")
                .setParameter("name", "%" + name + "%")
                .getResultList();
        return coursesGroups;
    }
    



}
