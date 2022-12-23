package com.pi.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.pi.entities.StudentsGroup;
import com.pi.entities.Student;
import com.pi.utils.JPAutil;

public class GroupDao {
	private EntityManager entityManager = JPAutil.getEntityManager("SchoolManagement");

	public void add(StudentsGroup studentsGroup) {
		entityManager.getTransaction().begin();
		entityManager.persist(studentsGroup);
		entityManager.getTransaction().commit();
	}

	public void update(StudentsGroup studentsGroup) {
		entityManager.getTransaction().begin();
		entityManager.merge(studentsGroup);
		entityManager.getTransaction().commit();
	}

	public void delete(StudentsGroup studentsGroup) {
		entityManager.getTransaction().begin();
		studentsGroup = entityManager.merge(studentsGroup);
		entityManager.remove(studentsGroup);
		entityManager.getTransaction().commit();
	}

	public StudentsGroup getGroupById(int id) {
		return entityManager.find(StudentsGroup.class, id);
	}

	// get all groups
	public List<StudentsGroup> getAllGroups() {
		return entityManager.createQuery("select g from StudentsGroup g").getResultList();
	}

	// get group by name
	public StudentsGroup getGroupByName(String name) {
		return (StudentsGroup) entityManager.createQuery("select g from StudentsGroup g where g.name = :name")
				.setParameter("name", name)
				.getSingleResult();
	}

	// get groups by level
	public List<StudentsGroup> getGroupsByLevel(String level) {
		return entityManager.createQuery("select g from StudentsGroup g where g.level = :level")
				.setParameter("level", level)
				.getResultList();
	}

	// get all students in a group
	public List<Student> getAllStudentsInGroup(int id) {
		return entityManager.createQuery("select s from Student s where s.group.id = :id").setParameter("id", id)
				.getResultList();
	}

}
