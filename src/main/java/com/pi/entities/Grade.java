package com.pi.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity

public class Grade implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_Grade;

	private int value;
	
	@ManyToOne
	@JoinColumn(name = "subscription_number")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "id_Course")
	private Course course;

	// grade has a single grade type
	@ManyToOne
	@JoinColumn(name = "id_GradeType")
	private GradeType gradeType;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Long getId() {
		return id_Grade;
	}

	public void setId(Long id) {
		this.id_Grade = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public GradeType getGradeType() {
		return gradeType;
	}

	public void setGradeType(GradeType gradeType) {
		this.gradeType = gradeType;
	}

	// toString
	@Override
	public String toString() {
		return "Grade [id_Grade=" + id_Grade + ", value=" + value + ", student=" + student + ", course=" + course
				+ ", gradeType=" + gradeType + "]";
	}

}
