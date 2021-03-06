package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.entity.Permission;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.LoginRequest;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.SearchRequest;
import com.github.yaroslavguschak.onlinelibrary.util.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LibraryController {

    @Autowired
    BookDAO bookDAO;

    @Inject
    User user;

    @Inject
    Alert alert;

    @Autowired
    DateFormat dateFormat;


    @RequestMapping(value = "/index")
    public ModelAndView showIndex()  {
        final ModelAndView mav = new ModelAndView("/index");
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

}
