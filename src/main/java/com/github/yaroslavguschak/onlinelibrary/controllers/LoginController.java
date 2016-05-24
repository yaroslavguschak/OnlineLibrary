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


@Controller
public class LoginController {
    @Autowired
    BookDAO dao;

    @Autowired
    UserDAO userDAO;
//
//    @Inject
//    User user;

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


    @RequestMapping(value = "/loginchek", method= RequestMethod.POST)
    public ModelAndView loginCheck(@ModelAttribute("loginRequest") LoginRequest loginRequest, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, GeneralSecurityException {
        String referrerURI = req.getHeader("referer");
        String login = loginRequest.getLogin();
        HttpSession httpSession = req.getSession(true);

        if (userDAO.checkUserLogin(login)){
            httpSession.setAttribute("message", "User with login " + login + "not exist");

        }
        else {

        }
        String passwordhashFromRequest = CSHA1Util.getSHA1String(loginRequest.getPassword());
        User user = userDAO.getUserByLogin(login);



        if (passwordhashFromRequest.equals(user.getPasswordhash())){
            System.out.println("User logged successful: " + login + " in time" + httpSession.getCreationTime());
            httpSession.setAttribute("user", user);
        } else {
        }
        return new ModelAndView("redirect:/" + UriReferrerCutter.cutReferre(referrerURI));
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