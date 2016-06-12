package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.dao.UserDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.entity.Permission;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.BookRequest;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.SearchRequest;
import com.github.yaroslavguschak.onlinelibrary.util.Alert;
import com.github.yaroslavguschak.onlinelibrary.util.UriReferrerCutter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.util.List;

@Controller
public class AdminController {

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



    @RequestMapping(value = "/adminaction" , method= RequestMethod.POST)
    public ModelAndView adminAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, GeneralSecurityException {

        String referrerURI = req.getHeader("referer");
        referrerURI = UriReferrerCutter.cutReferre(referrerURI);

        if( user.getPermission() == Permission.ADMIN) { // of course, view doesn't generate form for non-Admin user. Do for security.
            Long   bookId = Long.valueOf(req.getParameter("bookId"));
            String action = req.getParameter("action");
            Book book = bookDAO.getBookById(bookId);

            if ("Edit".equals(action)) {
                System.out.print("LOG: " + "user " + user.getLogin()+ " sent book # " + bookId + " " + book.getTitle() +  " for editing");
                BookRequest bookRequest = new BookRequest(book);
                final ModelAndView mav = new ModelAndView("edit");
                mav.addObject("searchRequest", new SearchRequest());
                mav.addObject("showuser", user);
                mav.addObject("bookRequest", bookRequest);
                return mav;
            }

            if ("Delete".equals(action)) {
                System.out.println("LOG: " + "user " + user.getLogin()+ " delete # " + bookId + " " + book.getTitle() +  " for editing");
                user.getShelf().delFromShelf(book);
                bookDAO.deleteBook(book);
            }
        }
     return new ModelAndView("redirect:/" + referrerURI);
    }
}
