package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.entity.Permission;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LibraryController {

    @Autowired
    BookDAO bookDAO;



    @RequestMapping(value = "/library")
    public ModelAndView showNews(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession(true);
        final ModelAndView mav = new ModelAndView("/library");
        List<Book> allBooksInLibrary = bookDAO.getAllBooks();
        User user = (User)httpSession.getAttribute("user");

        List<Integer> isSaved = new ArrayList<>(allBooksInLibrary.size());

        if (user != null){
            List<Book> booksSavedOnShelf = user.getShelf().getBookList();

            for (int i = 0; i < allBooksInLibrary.size(); ++i) {
                isSaved.add(i,-100);
                for (Book bookSaved:booksSavedOnShelf) {
                    if (allBooksInLibrary.get(i).getId().equals(bookSaved.getId())){
                        isSaved.add(i,100);
                    }
                }
            }
            mav.addObject("showuser", user);
            httpSession.setAttribute("bookList", allBooksInLibrary);
            mav.addObject("bookList", allBooksInLibrary);
            mav.addObject("isSaved", isSaved);
            return mav;
        } else  {
            mav.addObject("bookList", allBooksInLibrary);
            return mav;
        }
    }


    @RequestMapping(value = "/index")
    public ModelAndView showIndex(HttpServletRequest req, HttpServletResponse resp) throws GeneralSecurityException {
        HttpSession httpSession = req.getSession(true);
        User user = (User)httpSession.getAttribute("user");
        final ModelAndView mav = new ModelAndView("/index");
        mav.addObject("showuser", user);
        mav.addObject("loginRequest",new LoginRequest()); // спочатку перевірка, чи є в сесії користувач; якщо не має, кладеться реквест, якщо є - користувач
        mav.addObject("bookList", bookDAO.getAllBooks());

        return mav;
    }





}
