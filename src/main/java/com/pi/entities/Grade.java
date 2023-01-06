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
	private float coef;
	 @ManyToOne
	 @JoinColumn (name="Student_ID")
	 private Student student;
	 
	 @ManyToOne
	 @JoinColumn (name="Course_ID")
	 private Course course;
	 
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public float getCoef() {
		return coef;
	}
	public void setCoef(float coef) {
		this.coef = coef;
	}
	public Long getId() {
		return id_Grade;
	}
	
	

}