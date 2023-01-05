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

public class Department implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_Dept;
    private String name;
    @ManyToMany 
    @JoinTable( name = "T_Dept_CoursesGrp_Associations",
    joinColumns = @JoinColumn( name = "id_Dept" ),
    inverseJoinColumns = @JoinColumn( name = "id_CoursesGrp" ) )
    private List<CoursesGroup> CoursesGroup;
    

    @OneToMany (mappedBy="department") 
    //sans (mappedBy="d") une table intermédiare departement_employee sera crée
	private List<StudentsGroup> studentsGroup;
    
   
    
    

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public Long getId() {
        return id_Dept;
    }

    public void setId(Long id) {
        this.id_Dept = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department [id=" +id_Dept + ", name=" + name + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id_Dept == null) ? 0 : id_Dept.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        Department other = (Department) object;
        if (id_Dept == null) {
            return other.id_Dept == null;
        } else {
            return id_Dept.equals(other.id_Dept);
        }
    }

}
