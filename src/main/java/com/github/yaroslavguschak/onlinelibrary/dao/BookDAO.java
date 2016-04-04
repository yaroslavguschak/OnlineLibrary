package com.github.yaroslavguschak.onlinelibrary.dao;

import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Component
public class BookDAO {


    @Autowired
    public EntityManagerFactory entityManagerFactory;

    public void addBooksToLibrary(List<Book> bookList){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        for (Book book : bookList ){
            entityManager.persist(book);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }


    public List <Book> getAllBooks(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List <Book> bookList = (List <Book>) entityManager.createQuery("select e from Book e").getResultList();
        entityManager.close();
        return bookList;
    }



   /////// OK



    /////// OK
    public void saveNewBook (Book book){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
        entityManager.close();
        System.out.println("LOG / new Book add to DB" + book);

    }









    public List <Book> getAllUser(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List <Book> bookList = (List <Book>) entityManager.createQuery("select e from Book e").getResultList();
        entityManager.close();
        return bookList;
    }





}
