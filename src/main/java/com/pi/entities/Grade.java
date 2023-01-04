package com.pi.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Grade {
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private int value;
	private float coef;
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
		return id;
	}
	
	

}
