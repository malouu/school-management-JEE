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
import javax.persistence.OneToMany;

@Entity

public class Course implements Serializable{
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_Course;
    
    private String name;
    private float coef;
   
    @OneToMany (mappedBy="course") 
    //sans (mappedBy="d") une table intermédiare departement_employee sera crée
	private List<Grade> Grade;
    @ManyToMany 
    @JoinTable( name = "T_Courses_CoursesGrp_Associations",
    joinColumns = @JoinColumn( name = "id_Course" ),
    inverseJoinColumns = @JoinColumn( name = "id_CoursesGrp" ) )
    private List<CoursesGroup>CoursesGroups;
    @ManyToMany 
    @JoinTable( name = "T_GradeType_Courses_Associations",
    joinColumns = @JoinColumn( name = "id_Course" ),
    inverseJoinColumns = @JoinColumn( name = "id_GradeType" ) )
    private List<GradeType> GradeType;
    
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
