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

@Entity

public class GradeType implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_GradeType;

	@ManyToMany
	@JoinTable(name = "T_GradeType_Courses_Associations", joinColumns = @JoinColumn(name = "id_GrageType"), inverseJoinColumns = @JoinColumn(name = "id_Courses"))
	private List<Course> Course;
	private String name;

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

}
