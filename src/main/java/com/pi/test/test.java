package com.pi.test;

import java.util.List;

import com.pi.dao.GroupDao;
import com.pi.dao.StudentDao;
import com.pi.entities.Student;
import com.pi.entities.StudentsGroup;

public class test {
	public static void main(String[] args) {
		GroupDao deptDao = new GroupDao(); 
		StudentDao stDao = new StudentDao();
		
		
		StudentsGroup grp1 = new StudentsGroup();
		grp1.setName("IRM2");
		
		Student s1 = new Student();
		Student s2 = new Student();
		
		s1.setName("Yassine Bel Hadj");
		s1.setGroup(grp1);
		
		s2.setName("Nadhem Bel Hadj");
		s2.setGroup(grp1);
		
        //persister l'objet grp1
		deptDao.add(grp1);
		
		//persister les objets s1 et s2
		stDao.add(s1);
		stDao.add(s2);
		
		//affihcer la liste des etudiants
		List<Student> sts= deptDao.getAllStudentsInGroup(grp1.getId());
		
	
		for (int i = 0; i < sts.size(); i++) {
		      System.out.println(sts.get(i));
		    }

	}

}
