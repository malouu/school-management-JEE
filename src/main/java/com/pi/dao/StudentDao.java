package com.pi.dao;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;



import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.pi.entities.Student;
import com.pi.utils.JPAutil;
@Named
@ApplicationScoped
public class StudentDao {
	private EntityManager entityManager = JPAutil.getEntityManager("SchoolManagement");

	public void add(Student student) {
		entityManager.getTransaction().begin();
		entityManager.persist(student);
		entityManager.getTransaction().commit();
	}

	public void update(Student student) {
		entityManager.getTransaction().begin();
		entityManager.merge(student);
		entityManager.getTransaction().commit();
	}

	public void delete(Student student) {
		GradeDao gradeDao = new GradeDao();
		gradeDao.deleteStudentGrades(student);
		entityManager.getTransaction().begin();
		student = entityManager.merge(student);
		entityManager.remove(student);
		entityManager.getTransaction().commit();
	}

	// get student by id
	public Student getStudentById(int id) {
		return entityManager.find(Student.class, id);
	}

	// get all students
	public List<Student> getAllStudents() {
		List<Student> students = entityManager.createQuery("select s from Student s").getResultList();
		return students;
	}

	// get student by name
	public List<Student> getStudentByName(String name) {
		List<Student> students = entityManager.createQuery("select s from Student where s.name like :name")
				.setParameter("name", "%" + name + "%")
				.getResultList();
		return students;
	}

	// get student by surname
	public List<Student> getStudentBySurname(String surname) {
		List<Student> students = entityManager.createQuery("select s from Student where s.surname like :surname")
				.setParameter("surname", "%" + surname + "%")
				.getResultList();
		return students;
	}

	// get student by email
	public List<Student> getStudentByEmail(String email) {
		List<Student> students = entityManager.createQuery("select s from Student where s.email like :email")
				.setParameter("email", "%" + email + "%")
				.getResultList();
		return students;
	}

	// get student by phone
	public List<Student> getStudentByPhone(String phone) {
		List<Student> students = entityManager.createQuery("select s from Student where s.phone_number = :phonenumber")
				.setParameter("phonenumber", phone)
				.getResultList();
		return students;
	}

	// get all emails
	public List<String> getAllEmails() {
		List<String> emails = entityManager.createQuery("select email from Student").getResultList();
		return emails;
	}
	

}
