package com.pi.dao;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.pi.entities.StudentsGroup;
import com.pi.entities.Department;
import com.pi.entities.Student;
import com.pi.utils.JPAutil;

@Named
@ApplicationScoped

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
	//get studentsgroup by department id 
	public List<StudentsGroup> getStudentsByDepartmentId(long id) {
		

		
		return entityManager.createQuery("select * FROM StudentsGroup g where g.dept_ID ="+id).setParameter("id",id)
				.getResultList();
	}
	//main method
	public static void main(String[] args) {
		//add department
		DepartmentDao departmentDao = new DepartmentDao();
		Department department = new Department();
		department.setName("D1");

		GroupDao groupDao = new GroupDao();
		StudentsGroup studentsGroup = new StudentsGroup();
		studentsGroup.setName("G1");
		studentsGroup.setLevel("L1");
		//set department 
		studentsGroup.setDepartment(department);
		groupDao.add(studentsGroup);
		//test getStudentsByDepartmentId 
		//System.out.println(groupDao.getStudentsByDepartmentId(1));
	}


}
