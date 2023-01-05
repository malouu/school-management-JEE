package com.pi.dao;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.pi.entities.User;
import com.pi.utils.JPAutil;

@Named
@ApplicationScoped

public class UserDao {

    private EntityManager entityManager = JPAutil.getEntityManager("SchoolManagement");

    public void add(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    public void update(User user) {
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    public void delete(User user) {
        entityManager.getTransaction().begin();
        user = entityManager.merge(user);
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }

    // get user by id
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    // get all users
    public List<User> getAllUsers() {
        return entityManager.createQuery("select c from User c").getResultList();
    }

}
