package com.github.yaroslavguschak.onlinelibrary.dao;

import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.entity.Genre;
import com.github.yaroslavguschak.onlinelibrary.entity.Permission;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.security.GeneralSecurityException;
import java.util.List;

@Component
public class BookDAO {

    @Autowired
    UserDAO userDAO;

    @Autowired
    public EntityManagerFactory entityManagerFactory;

    public Book getBookById(Long bookId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
//            Book book = (entityManager.createNamedQuery("Book.getBookById", Book.class).setParameter("bookid", bookId))
//                    .getSingleResult();
            return entityManager.find(Book.class, bookId);
        } finally {
            entityManager.close();
        }
    }

    private List <Book> searchBooksByTitleAndAuthor(SearchRequest searchRequest, EntityManager entityManager){
        try {
            List <Book> bookList = entityManager.createNamedQuery("Book.searchBookByTitleAndAuthor", Book.class)
                    .setParameter("search", "%" +  searchRequest.getSearchTextInput() + "%").getResultList();
            return bookList;
        } finally {
            entityManager.close();
        }
    }

    private List <Book> searchBooksByTitleAndAuthorAndGenre(SearchRequest searchRequest, EntityManager entityManager){
        try {
            List <Book> bookList = entityManager.createNamedQuery("Book.searchBookByTitleAndAuthorAndGenre", Book.class)
                    .setParameter("search", "%" +  searchRequest.getSearchTextInput() + "%").
                            setParameter("genre", searchRequest.getGenre()).getResultList();
            return bookList;
        } finally {
            entityManager.close();
        }
    }

    public List <Book> searchBooks(SearchRequest searchRequest){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        if (searchRequest.getGenre() == Genre.NONE){
            return searchBooksByTitleAndAuthor(searchRequest,entityManager);
        } else {
            return searchBooksByTitleAndAuthorAndGenre(searchRequest,entityManager);
        }
    }




    public Integer deleteBookById(Long bookId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("DELETE FROM Book b WHERE b.id = :bookid");
            Integer deletedCount = query.setParameter("bookid", bookId).executeUpdate();
            return deletedCount;
        } finally {
            entityManager.close();
        }
    }

    public void deleteBook(Book book) throws GeneralSecurityException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List <User> allBookOwnerList = userDAO.getAllBookOwner(book);
        System.out.println("======///USERS FOR DEL BOOK///=========");
        System.out.println(allBookOwnerList.toString() + "SIZE:  " + allBookOwnerList.size());
        System.out.println("======///USERS FOR DEL BOOK///=========");

        for (User user : allBookOwnerList) {
             user.getShelf().getBookList().remove(book);
            userDAO.updateUser(user);
        }


        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(book) ? book : entityManager.merge(book));
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }


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


    public void saveNewBook (Book book){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
        entityManager.close();
        System.out.println("LOG / new Book add to DB" + book);

    }


    public void updateBook (Book book){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(book);
        entityManager.getTransaction().commit();
        entityManager.close();
        System.out.println("LOG / Book updated in  DB" + book.toString());
    }









//    public List <Book> getAllUser(){
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//
//        List <Book> bookList = (List <Book>) entityManager.createQuery("select e from Book e").getResultList();
//        entityManager.close();
//        return bookList;
//    }





}
