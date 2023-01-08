package com.pi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import java.io.Serializable;
import java.util.List;

import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity

public class GradeType implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_GradeType;

	@ManyToMany
	@JoinTable(name = "T_GradeType_Courses_Associations", joinColumns = @JoinColumn(name = "id_GradeType"), inverseJoinColumns = @JoinColumn(name = "id_Course"))
	private List<Course> Course;
	private String name;
	@OneToMany(mappedBy = "gradeType") // sans (mappedBy="d") une table intermédiare
	// departement_employee sera créée

	private List<Grade> grades;

	private float coef;

	public String getName() {
		return name;
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
		return id_GradeType;
	}

	public GradeType(String name, float coef) {
		this.name = name;
		this.coef = coef;
	}

	public GradeType() {
		this.name = "";
		this.coef = 0;
	}

	@Override
	public String toString() {
		return "GradeType [id_GradeType=" + id_GradeType + ", name=" + name + ", coef=" + coef + "]";
	}

	// equals
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!GradeType.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final GradeType other = (GradeType) obj;
		if ((this.id_GradeType == null) ? (other.id_GradeType != null)
				: !this.id_GradeType.equals(other.id_GradeType)) {
			return false;
		}
		return true;
	}

}
