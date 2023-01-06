package com.pi.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

import java.io.Serializable;



@Entity

public class CoursesGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_CoursesGrp;
    
    private String name;
    private String coef;
    
    @ManyToMany 
    @JoinTable( name = "T_Courses_CoursesGrp_Associations",
    joinColumns = @JoinColumn( name = "id_CoursesGrp" ),
    inverseJoinColumns = @JoinColumn( name = "id_Course" ) )
    private List<Course>courses;
    
    @ManyToMany 
    @JoinTable( name = "T_Dept_CoursesGrp_Associations",
    joinColumns = @JoinColumn( name = "id_CoursesGrp" ),
    inverseJoinColumns = @JoinColumn( name = "id_Dept" ) )
    private List<Department>department;
    
    @ManyToMany 
    @JoinTable( name = "T_GRP_CoursesGrp_Associations",
    joinColumns = @JoinColumn( name = "id_CoursesGrp" ),
    inverseJoinColumns = @JoinColumn( name = "id_GRP" ) )
    private List<StudentsGroup>studentsGroup;
  

   
  
    
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
		return id_CoursesGrp;
	}
    
	public List<Course> getCourses()
	{
		return courses;
	}
    
    

}
