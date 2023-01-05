package com.pi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import java.io.Serializable;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity

public class GradeType implements Serializable {
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
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
		return id;
	}
    
    

}
