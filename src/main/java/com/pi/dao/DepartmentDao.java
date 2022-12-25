package com.pi.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.pi.entities.Department;
import com.pi.utils.JPAutil;

public class DepartmentDao {
	private EntityManager entityManager = JPAutil.getEntityManager("SchoolManagement");

    public void add(Department department) {
        entityManager.getTransaction().begin();
        entityManager.persist(department);
        entityManager.getTransaction().commit();
    }

    public void update(Department department) {
        entityManager.getTransaction().begin();
        entityManager.merge(department);
        entityManager.getTransaction().commit();
    }

    public void delete(Department department) {
        entityManager.getTransaction().begin();
        department = entityManager.merge(department);
        entityManager.remove(department);
        entityManager.getTransaction().commit();
    }

    public List<Department> getAllDepartments() {
        return entityManager.createQuery("SELECT d FROM Department d", Department.class).getResultList();
    }

    public Department getDepartmentById(int id) {
        return entityManager.find(Department.class, id);
    }

    // get departments by name
    public List<Department> getDepartmentByName(String name) {

        List<Department> departments = entityManager.createQuery("SELECT d FROM Department d where d.name like :name",
                Department.class).setParameter("name", "%" + name + "%").getResultList();
        return departments;
    }

}
