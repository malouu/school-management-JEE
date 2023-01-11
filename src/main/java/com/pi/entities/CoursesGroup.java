package com.pi.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
    private int coef;

    @ManyToMany
    @JoinTable(name = "T_Courses_CoursesGrp_Associations", joinColumns = @JoinColumn(name = "id_CoursesGrp"), inverseJoinColumns = @JoinColumn(name = "id_Course"))
    private List<Course> courses;

    @ManyToMany
    @JoinTable(name = "T_Dept_CoursesGrp_Associations", joinColumns = @JoinColumn(name = "id_CoursesGrp"), inverseJoinColumns = @JoinColumn(name = "id_Dept"))
    private List<Department> department;

    @ManyToMany
    @JoinTable(name = "T_GRP_CoursesGrp_Associations", joinColumns = @JoinColumn(name = "id_CoursesGrp"), inverseJoinColumns = @JoinColumn(name = "id_GRP"))
    private List<StudentsGroup> studentsGroup;

    
    public CoursesGroup()
    {}
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoef() {
        return coef;
    }

    public void setCoef(int coef) {
        this.coef = coef;
    }

    public Long getId() {
        return id_CoursesGrp;
    }

    public void setId(Long id) {
        this.id_CoursesGrp = id;
    }

    public List<Course> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        return "CoursesGroup{" +
                "id=" + id_CoursesGrp +
                ", name='" + name + '\'' +
                ", coef='" + coef + '\'' +
                '}';
    }

	@Override
	public int hashCode() {
		return Objects.hash(coef, courses, department, id_CoursesGrp, name, studentsGroup);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoursesGroup other = (CoursesGroup) obj;
		return coef == other.coef && Objects.equals(courses, other.courses)
				&& Objects.equals(department, other.department) && Objects.equals(id_CoursesGrp, other.id_CoursesGrp)
				&& Objects.equals(name, other.name) && Objects.equals(studentsGroup, other.studentsGroup);
	}
    
    

}
