package com.pi.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity

public class Course implements Serializable, Comparable<Course> {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_Course;

	private String name;
	private float coef;

	@OneToMany(mappedBy = "course")
	// sans (mappedBy="d") une table intermédiare departement_employee sera crée
	private List<Grade> Grades;
	@ManyToMany
	@JoinTable(name = "T_Courses_CoursesGrp_Associations", joinColumns = @JoinColumn(name = "id_Course"), inverseJoinColumns = @JoinColumn(name = "id_CoursesGrp"))
	private List<CoursesGroup> CoursesGroups;
	@ManyToMany
	@JoinTable(name = "T_GradeType_Courses_Associations", joinColumns = @JoinColumn(name = "id_Course"), inverseJoinColumns = @JoinColumn(name = "id_GradeType"))
	private List<GradeType> gradeTypes;

	public String getName() {
		return name;
	}
	
	public Course()
	{}

	public Course(String name, float coef) {
		super();
		this.name = name;
		this.coef = coef;
	}


	public void setName(String name) {
		this.name = name;
	}

	public float getCoef() {
		return coef;
	}

	public void setCoef(float coef) {
		this.coef = coef;
	}

	public Long getId() {
		return id_Course;
	}

	public void setId(Long id) {
		this.id_Course = id;
	}

	public void setGradeTypes(List<GradeType> gradeTypes) {

		this.gradeTypes = gradeTypes;
	}

	// equals. test on id, name and coef
	@Override
	// public boolean equals(Object obj) {
	// if (obj == null) {
	// return false;
	// }
	// if (!Course.class.isAssignableFrom(obj.getClass())) {
	// return false;
	// }
	// final Course other = (Course) obj;
	// if ((this.name == null) ? (other.name != null) :
	// !this.name.equals(other.name)) {
	// return false;
	// }
	// if (this.coef != other.coef) {
	// return false;
	// }
	// if (this.id_Course != other.id_Course && (this.id_Course == null ||
	// !this.id_Course.equals(other.id_Course))) {
	// return false;
	// }
	// return true;
	// }

	// only test on id
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!Course.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final Course other = (Course) obj;
		if (this.id_Course != other.id_Course && (this.id_Course == null || !this.id_Course.equals(other.id_Course))) {
			return false;
		}
		return true;
	}

	// to string
	@Override
	public String toString() {
		return "Course [id_Course=" + id_Course + ", name=" + name + ", coef=" + coef + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_Course, name, coef);
	}

	@Override
	public int compareTo(Course o) {
		return this.name.compareTo(o.name);
	}
}
