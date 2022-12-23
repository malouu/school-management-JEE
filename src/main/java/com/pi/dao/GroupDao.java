package com.pi.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.pi.entities.Group;
import com.pi.entities.Student;
import com.pi.utils.JPAutil;

public class GroupDao {
	private EntityManager entityManager = JPAutil.getEntityManager("SchoolManagement");

	public void add(Group group) {
		entityManager.getTransaction().begin();
		entityManager.persist(group);
		entityManager.getTransaction().commit();
	}

	public void update(Group group) {
		entityManager.getTransaction().begin();
		entityManager.merge(group);
		entityManager.getTransaction().commit();
	}

	public void delete(Group group) {
		entityManager.getTransaction().begin();
		group = entityManager.merge(group);
		entityManager.remove(group);
		entityManager.getTransaction().commit();
	}

	public Group getGroupById(int id) {
		return entityManager.find(Group.class, id);
	}

	// get all groups
	public List<Group> getAllGroups() {
		return entityManager.createQuery("select g from Group g").getResultList();
	}

	// get group by name
	public Group getGroupByName(String name) {
		return (Group) entityManager.createQuery("select g from Group g where g.name = :name")
				.setParameter("name", name)
				.getSingleResult();
	}

	// get groups by level
	public List<Group> getGroupsByLevel(String level) {
		return entityManager.createQuery("select g from Group g where g.level = :level")
				.setParameter("level", level)
				.getResultList();
	}

	// get all students in a group
	public List<Student> getAllStudentsInGroup(int id) {
		return entityManager.createQuery("select s from Student s where s.group.id = :id").setParameter("id", id)
				.getResultList();
	}

}
