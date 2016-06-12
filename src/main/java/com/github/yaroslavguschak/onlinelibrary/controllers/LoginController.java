package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.util.Alert;
import com.github.yaroslavguschak.onlinelibrary.util.CSHA1Util;

import com.github.yaroslavguschak.onlinelibrary.dao.UserDAO;
import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.LoginRequest;

import com.github.yaroslavguschak.onlinelibrary.util.UriReferrerCutter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import java.util.Date;


@Controller
public class LoginController {
    @Autowired
    BookDAO dao;

    @Autowired
    UserDAO userDAO;

    @Inject
    User user;

    @Inject
    Alert alert;

    @Autowired
    DateFormat dateFormat;

    @RequestMapping(value = "/loginchek", method = RequestMethod.POST)
    public ModelAndView loginCheck(@ModelAttribute("loginRequest") LoginRequest loginRequest,
                                   HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, GeneralSecurityException {
        String referrerURI = req.getHeader("referer");
        referrerURI = UriReferrerCutter.cutReferre(referrerURI);

        String login = loginRequest.getLogin();

        if (userDAO.checkUserLogin(login)){
            String passwordhashFromRequest = CSHA1Util.getSHA1String(loginRequest.getPassword());


            if (passwordhashFromRequest.equals(userDAO.getPassHashByLogin(login))){
                System.out.println("User logged successful: " + login + " in time " + dateFormat.format(new Date()));
                user.copyAllFields(userDAO.getUserByLogin(login));
                System.out.println(user.toString());
            } else {
                alert.setMessage("pass for login [" + login + "]  is incorrect");
                alert.setShow(true);
//                req.getSession(true).invalidate();
            }
        } else {
            alert.setMessage("User with login [" + login + "] not exist");
            alert.setShow(true);
        }
        return new ModelAndView("redirect:/" + referrerURI );
    }


    @RequestMapping(value = "/logout", method= RequestMethod.GET)
    public ModelAndView logOut(HttpServletRequest req, HttpServletResponse resp) {
        String referrerURI = req.getHeader("referer");
        final ModelAndView mav = new ModelAndView("redirect:/" + UriReferrerCutter.cutReferre(referrerURI));

        HttpSession httpSession = req.getSession(true);
        httpSession.invalidate();
        LoginRequest loginRequest = new LoginRequest();
        mav.addObject("loginRequest",loginRequest);
        return mav;
    }





    ////////////////////DELETE//////////////////////////////
    @RequestMapping(value = "/user")
    public ModelAndView aboutUser(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession(true);
        User user = (User)httpSession.getAttribute("user");
        final ModelAndView mav = new ModelAndView("/user");
        mav.addObject("showuser", user);
        return mav;
    }




}