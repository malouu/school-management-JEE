package com.pi.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity

public class StudentsGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_GRP;
	private String name;
	private String level;

	@ManyToMany
	@JoinTable(name = "T_GRP_CoursesGrp_Associations", joinColumns = @JoinColumn(name = "id_GRP"), inverseJoinColumns = @JoinColumn(name = "id_CoursesGrp"))
	private List<CoursesGroup> coursesGroups;

	@ManyToOne
	@JoinColumn(name = "dept_ID")

	private Department department;

	@OneToMany(mappedBy = "group") // sans (mappedBy="d") une table intermédiare
	// departement_employee sera créée

	private List<Student> students;

	public StudentsGroup() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id_GRP;
	}

	public void setId(int id) {
		this.id_GRP = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<CoursesGroup> getCoursesGroups() {
		// TODO Auto-generated method stub
		return this.coursesGroups;
	}
	//
	// public List<Course> getCourses() {
	// // coursesGroups contains a list of coursesGroups. Each coursesGroup contains
	// a
	// // list of courses. Flatten the list of coursesGroups into a list of courses.
	// return this.coursesGroups.stream().map(cg -> cg.getCourses()).flatMap(c ->
	// c.stream())
	// .collect(Collectors.toList());
	// }

	@Override
	public String toString() {
		return "Group [id=" + id_GRP + ", name=" + name + ", level=" + level + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_GRP;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass())
			return false;
		StudentsGroup other = (StudentsGroup) obj;
		if (id_GRP != other.id_GRP)
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
