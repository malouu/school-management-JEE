package com.pi.services;

import com.pi.dao.StudentDao;
import com.pi.entities.Student;

public class StudentService {
	private final StudentDao stDao ;
	
	public StudentService(StudentDao stDao) {
		this.stDao = stDao;
	}
//add inexistent student
	public void addStudent(Student st) {
		if(stDao.getStudentById(st.getSubscription_number()) == null) {
			stDao.add(st);
		}
		throw new RuntimeException("Student already exists, The ID was found in the database");
		
	}
//update student
	public void updateStudent(Student st) {
		if(stDao.getStudentById(st.getSubscription_number()) != null) {
			stDao.update(st);
		}
		throw new RuntimeException("Student does not exist, The ID was not found in the database");
	}
	//delete student
	public void deleteStudent(Student st) {
		if(stDao.getStudentById(st.getSubscription_number()) != null) {
			stDao.delete(st);
		}
		throw new RuntimeException("Student does not exist, The ID was not found in the database");
	}
	


	
}
