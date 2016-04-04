package com.github.yaroslavguschak.onlinelibrary.dao;

import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.dao.UserDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.entity.Genre;
import com.github.yaroslavguschak.onlinelibrary.entity.Permission;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yars on 03.04.2016.
 */
public class Stock {

    @Autowired
    BookDAO bookDAO;

    @Autowired
    UserDAO userDAO;

    public Stock() throws GeneralSecurityException {
//        fillDB();
    }

//    private void fillDB() throws GeneralSecurityException {
//        if (bookDAO == null){
//            System.out.println("SUCKS-SUCKS-SUCKS-SUCKS-SUCKS-SUCKS-SUCKS-SUCKS-SUCKS");
//        }
//        Book book = new Book("Charles Dickens", "Great Expectations", Genre.DRAMA, 1861, "London", "256-369-456-584", 256, "Story about Great Expectations");
//        System.out.println(book.toString());
//        bookDAO.saveNewBook(book);
//        book = new Book("Ernest Hemingway", "The Old Man And The Sea", Genre.ROMANCE_NOVEL, 1925, "Cuba", "255-654-445-464", 315, "Story about The Old Man And The Sea");
//        bookDAO.saveNewBook(book);
//        book = new Book("Jack London", "White Fang", Genre.REALISTIC_FICTION, 1906, "New York", "978-185-813-634", 298, "Story about White Fang");
//        bookDAO.saveNewBook(book);
//        book = new Book("Панас Мирний", "Хіба ревуть воли, як ясла повні", Genre.TRAGEDY, 1865, "Київ", "464-155-813-634", 298, "Історія про волів і не тільки");
//        bookDAO.saveNewBook(book);
//
//        List <Book> bookList = bookDAO.getAllUser();
//
//        for (Book b : bookList) {
//            System.out.println(b.toString());
//        }
//
//
//
//        //String nickname, String firstName, String lastName, String email, Permission permission, String password
//        User user = new User("makl", "Yaroslav", "Guschak", "yar.guschak@gmail.com", Permission.ADMIN, "1111");
//
//        userDAO.saveNewUser(user);
//        user = new User("snake", "Vasyl", "Golodnyak", "snake@gmail.com", Permission.SUBSCRIBER, "2222");
//        userDAO.saveNewUser(user);
//        user = new User("zoRbik", "Andrew", "Zorba", "zorbik@gmail.com", Permission.GUEST, "3333");
//        userDAO.saveNewUser(user);
//        user = new User("dyNya", "Andrew", "Dynovskiy", "dynya@ya.ru", Permission.SUBSCRIBER, "4444");
//        userDAO.saveNewUser(user);
//        user = new User("murKa", "Yuriy", "Nosenko", "murka@rambler.ru", Permission.GUEST, "3333");
//        userDAO.saveNewUser(user);
//
//
//
//        List <User> userList = userDAO.getAllUser();
//
//        for (User u : userList) {
//            System.out.println(u.toString());
//        }
//
//    }
}
