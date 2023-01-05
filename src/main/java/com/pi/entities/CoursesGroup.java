package com.pi.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;



@Entity

public class CoursesGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private String coef;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCoef() {
		return coef;
	}
	public void setCoef(String coef) {
		this.coef = coef;
	}
	public Long getId() {
		return id;
	}
    
    
    

}
