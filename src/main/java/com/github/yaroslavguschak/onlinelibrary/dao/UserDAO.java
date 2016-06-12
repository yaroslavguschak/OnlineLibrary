package com.github.yaroslavguschak.onlinelibrary.dao;

import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.security.GeneralSecurityException;
import java.util.List;

@Component
public class UserDAO {

    @Autowired
    public EntityManagerFactory entityManagerFactory;

    public void saveNewUser (User user){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
        System.out.println("LOG / new User add to DB. User ID: " + user.getId() + " User login: " +  user.getLogin());

    }

    public User getUserById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(User.class, id);
        } finally {
            entityManager.close();
        }
    }

    private int getUserCountByLogin(EntityManager entityManager, String userLogin) {
        return (entityManager.createNamedQuery("User.getCountByLogin", Long.class).setParameter("userl", userLogin))
                .getSingleResult().intValue();
    }

    public String getPassHashByLogin(String userLogin) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return (entityManager.createNamedQuery("User.getPassHashByLogin", String.class).setParameter("userl", userLogin))
                .getSingleResult();
    }

    public boolean checkUserLogin(String userLogin) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        boolean ifExist = getUserCountByLogin(entityManager, userLogin) > 0;
        entityManager.close();
        return ifExist;
    }





    public User getUserByLogin(String userLogin) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            User user = (entityManager.createNamedQuery("User.getUserByLogin", User.class).setParameter("userl", userLogin))
                    .getSingleResult();
            return user;
        } finally {
            entityManager.close();
        }
    }

    public void updateUser (User user) throws GeneralSecurityException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User tempUser = new User();
        tempUser.copyAllFields(user);
        entityManager.merge(tempUser);
        entityManager.getTransaction().commit();
        entityManager.close();
        System.out.println("LOG / User updated in  DB" + user.toString());
    }


    public List <User> getAllUser(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();

        List <User> userList = (List <User>) entityManager.createQuery("select e from User e").getResultList();
        entityManager.close();
        return userList;
    }

    public List <User> getAllBookOwner(Book book){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List <User> userList = (List <User>) entityManager.createQuery("select u from User u, IN (u.shelf.bookList) b WHERE b.id = " + book.getId()).getResultList();
        entityManager.close();
        return userList;
    }




}
