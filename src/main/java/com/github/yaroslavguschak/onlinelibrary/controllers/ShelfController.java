package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.dao.UserDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Permission;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.BookRequest;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.LoginRequest;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.SearchRequest;
import com.github.yaroslavguschak.onlinelibrary.util.Alert;
import com.github.yaroslavguschak.onlinelibrary.util.UriReferrerCutter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.util.List;

@Controller
public class ShelfController {

    @Autowired
    BookDAO bookDAO;

    @Autowired
    UserDAO userDAO;

    @Inject
    User user;

    @Inject
    Alert alert;

    @Autowired
    DateFormat dateFormat;


    @RequestMapping(value = "/shelf")
    public ModelAndView showShelf(HttpServletRequest req, HttpServletResponse resp) {
        final ModelAndView mav = new ModelAndView("/shelf");
        mav.addObject("showuser", user);
        if (alert.getIsShow()){
            mav.addObject("message", alert.getMessage());
            alert.setShow(false);
        }
        if (user.getPermission() != Permission.GUEST) {
            mav.addObject("bookList", bookDAO.getAllBooks());
            mav.addObject("searchRequest", new SearchRequest());
        } else {
            mav.addObject("loginRequest", new LoginRequest());
        }
        return mav;
    }

    @RequestMapping(value = "/shelfaction" , method = RequestMethod.POST)
    public ModelAndView adminAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, GeneralSecurityException {

        if(user.getPermission() == Permission.ADMIN) { // of course, view doesn't generate form for non-Admin/Subs user. Do for security.
            user.copyAllFields(userDAO.getUserById(user.getId())); // check if another user with  ADMIN perm. del/edit some book(s) during the session
            Long   bookId = Long.valueOf(req.getParameter("bookId"));
            String action = req.getParameter("action");
            Book book = bookDAO.getBookById(bookId);

            if ("Save to shelf".equals(action)) {
                System.out.print("LOG: " + "user " + user.getLogin()+ " Save book # " + bookId + " " + book.getTitle() +  " to Shelf");
                user.getShelf().addBook(book);
                userDAO.updateUser(user);
            }

            if ("Delete from shelf".equals(action)) {
                System.out.println("LOG: " + "user " + user.getLogin()+ " delete # " + bookId + " " + book.getTitle() +  " for editing");
                user.getShelf().delFromShelf(book);
                userDAO.updateUser(user);
            }
            String referrerURI = req.getHeader("referer");
            return new ModelAndView("redirect:/" + UriReferrerCutter.cutReferre(referrerURI));

        } else {
            return new ModelAndView("redirect:/shelf");
        }
    }
}




