package com.pi.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity

public class Course implements Serializable{
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_Course;
    
    private String name;
    private float coef;
    @ManyToMany 
    @JoinTable( name = "hello",
    joinColumns = @JoinColumn( name = "id_Course" ),
    inverseJoinColumns = @JoinColumn( name = "subscription_number" ) )
    private List<Student> Student;

    
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
		return id_Course;
	}
    
    

}
