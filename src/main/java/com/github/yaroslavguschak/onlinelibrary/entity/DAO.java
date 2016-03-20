package com.github.yaroslavguschak.onlinelibrary.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.client.WebTarget;
import java.util.List;

@Component
public class DAO {


    @Autowired
    public EntityManagerFactory entityManagerFactory;

    public void addToUserShelf(List<Book> bookList){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        for (Book book : bookList ){
            entityManager.persist(book);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void saveDefoltUser(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = new User();
        System.out.println("==================");
        System.out.println("LOG / new User created" + user.toString());
        System.out.println("==================");
        entityManager.persist(user);

        entityManager.getTransaction().commit();
        entityManager.close();
    }



   /////// OK
    public void saveNewUser (User user){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
        System.out.println("LOG / new User add to DB" + user.toString());

    }


    /////// OK
    public void saveNewBook (Book book){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
        entityManager.close();
        System.out.println("LOG / new Book add to DB" + book);

    }







    public List <Book> getUserShelf(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List <Book> bookList = (List <Book>) entityManager.createQuery("select e from Book e").getResultList();
        entityManager.close();
        return bookList;
    }





}
