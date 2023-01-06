package com.pi.services;

import com.pi.dao.StudentDao;
import com.pi.entities.Student;
import com.pi.entities.Grade;
import com.pi.entities.Course;
import java.util.List;
import java.pi.services.GroupService;

public class StudentService {
	private final StudentDao stDao;
	private Student student;
	private GroupService groupService;

	public StudentService(StudentDao stDao) {
		this.stDao = stDao;
	}

	// add inexistent student
	public void addStudent(Student st) {
		if (stDao.getStudentById(st.getSubscription_number()) == null) {
			stDao.add(st);
		}
		throw new RuntimeException("Student already exists, The ID was found in the database");

	}

	// update student
	public void updateStudent(Student st) {
		if (stDao.getStudentById(st.getSubscription_number()) != null) {
			stDao.update(st);
		}
		throw new RuntimeException("Student does not exist, The ID was not found in the database");
	}

	// delete student
	public void deleteStudent(Student st) {
		if (stDao.getStudentById(st.getSubscription_number()) != null) {
			stDao.delete(st);
		}
		throw new RuntimeException("Student does not exist, The ID was not found in the database");
	}

	public float calculateCourseAverage(Course course) {
		float average = 0;
		List<Grade> grades = student.getGrades(course);
		for (Grade grade : grades) {
			average += grade.getValue() * grade.getCoef();
		}
		return average / grades.size();
	}

	public float calculateAverage() {
		float average = 0;
		float coef = 0;
		groupService.setGroup(student.getGroup());
		List<Course> courses = groupService.getCourses();
		for (Course course : courses) {
			average += calculateCourseAverage(course) * course.getCoef();
			coef += course.getCoef();
		}
		return average / coef;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
}
