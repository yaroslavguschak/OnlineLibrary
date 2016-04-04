package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.dao.Stock;
import com.github.yaroslavguschak.onlinelibrary.dao.UserDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.entity.Genre;
import com.github.yaroslavguschak.onlinelibrary.entity.Permission;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.GeneralSecurityException;
import java.util.List;

@Controller
public class TestController {


    @Autowired
    BookDAO bookDAO;

    @Autowired
    UserDAO userDAO;

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test() throws GeneralSecurityException {

        return  fillDB();
    }



    private String fillDB() throws GeneralSecurityException {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Wellcome in test controller");

        Book book = new Book("Charles Dickens", "Great Expectations", Genre.DRAMA, 1861, "London", "256-369-456-584", 256, "Story about Great Expectations");
        bookDAO.saveNewBook(book);
        book = new Book("Ernest Hemingway", "The Old Man And The Sea", Genre.ROMANCE_NOVEL, 1925, "Cuba", "255-654-445-464", 315, "Story about The Old Man And The Sea");
        bookDAO.saveNewBook(book);
        book = new Book("Jack London", "White Fang", Genre.REALISTIC_FICTION, 1906, "New York", "978-185-813-634", 298, "Story about White Fang");
        bookDAO.saveNewBook(book);
        book = new Book("Панас Мирний", "Хіба ревуть воли, як ясла повні", Genre.TRAGEDY, 1865, "Київ", "464-155-813-634", 298, "Історія про волів і не тільки");
        bookDAO.saveNewBook(book);

        List<Book> bookList = bookDAO.getAllUser();

        for (Book b : bookList) {
            System.out.println(b.toString());
            stringBuffer.append(b.toString());
        }



        //String nickname, String firstName, String lastName, String email, Permission permission, String password
        User user = new User("makl", "Yaroslav", "Guschak", "yar.guschak@gmail.com", Permission.ADMIN, "1111");

        userDAO.saveNewUser(user);
        user = new User("snake", "Vasyl", "Golodnyak", "snake@gmail.com", Permission.SUBSCRIBER, "2222");
        userDAO.saveNewUser(user);
        user = new User("zoRbik", "Andrew", "Zorba", "zorbik@gmail.com", Permission.GUEST, "3333");
        userDAO.saveNewUser(user);
        user = new User("dyNya", "Andrew", "Dynovskiy", "dynya@ya.ru", Permission.SUBSCRIBER, "4444");
        userDAO.saveNewUser(user);
        user = new User("murKa", "Yuriy", "Nosenko", "murka@rambler.ru", Permission.GUEST, "3333");
        userDAO.saveNewUser(user);



        List <User> userList = userDAO.getAllUser();

        for (User u : userList) {
            System.out.println(u.toString());
            stringBuffer.append(u.toString());
        }

        return stringBuffer.toString();

    }






}
