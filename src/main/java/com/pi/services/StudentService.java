package com.pi.services;

import com.pi.dao.GradeDao;
import com.pi.dao.StudentDao;
import com.pi.entities.Student;
import com.pi.entities.Grade;
import com.pi.entities.GradeType;
import com.pi.entities.Course;
import java.util.List;
import com.pi.services.GroupService;

public class StudentService {
	private final StudentDao stDao;
	private Student student;
	private GroupService groupService;
	private Course course;
	private GradeDao gradeDao = new GradeDao();

	public StudentService(StudentDao stDao) {
		this.stDao = stDao;
	}

	public StudentService() {
		this.stDao = new StudentDao();
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
		List<Grade> grades = student.getGrades();

		// filter grades by course
		grades.removeIf(grade -> grade.getCourse().getId() != course.getId());
		for (Grade grade : grades) {
			average += grade.getValue() * grade.getGradeType().getCoef();
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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Grade getStudentCourseGradewithType(Student s, GradeType gradeType) {
		// student has a list of grades. Each grade has a gradeType. If the grades are
		// not empty, return the grade that has the same course and gradeType. If not,
		// create a new grade with the course and gradeType for the student, give it a
		// value of 0 and return it.
		System.out.println("getStudentCourseGrade");

		if (!s.getGrades().isEmpty()) {
			// System.out.println("getStudentCourseGrade not empty");
			for (Grade grade : s.getGrades()) {
				if (grade.getCourse().equals(course) && grade.getGradeType().equals(gradeType)) {
					return grade;
				}
			}
		}
		Grade grade = new Grade();
		grade.setCourse(course);
		grade.setGradeType(gradeType);
		grade.setValue(0);
		grade.setStudent(s);
		System.out.println(grade.toString());
		gradeDao.add(grade);
		s.addGrade(grade);
		return grade;
	}
}
