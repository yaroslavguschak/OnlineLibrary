package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.util.CSHA1Util;

import com.github.yaroslavguschak.onlinelibrary.dao.UserDAO;
import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
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


    @RequestMapping(value = "/loginchek", method= RequestMethod.POST)
    @ResponseBody
    public void loginCheck(@ModelAttribute("loginRequest") LoginRequest loginRequest, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, GeneralSecurityException {
        String login = loginRequest.getLogin();
        String passwordhashFromRequest = CSHA1Util.getSHA1String(loginRequest.getPassword());
        User user = userDAO.getUserByLogin(login);

        if (passwordhashFromRequest.equals(user.getPasswordhash())){
            HttpSession httpSession = req.getSession(true);
            System.out.println("User logged successful: " + login + " in time" + httpSession.getCreationTime());
            httpSession.setAttribute("user", user);
            RequestDispatcher requestDispatcher;
            requestDispatcher = req.getRequestDispatcher("/user");
            requestDispatcher.forward(req, resp);

        } else {
            final ModelAndView mav = new ModelAndView("/login");
            mav.addObject("loginRequest",loginRequest);
            String error = "login/pass is incorrect!";
            mav.addObject("error",error);
        }

    }

    @RequestMapping(value = "/login", method= RequestMethod.GET)
    public ModelAndView loginGet() {
        final ModelAndView mav = new ModelAndView("/login");
        LoginRequest loginRequest = new LoginRequest();
        mav.addObject("loginRequest",loginRequest);
        return mav;
    }

    @RequestMapping(value = "/user")
    public ModelAndView aboutUser(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession(true);
        User user = (User)httpSession.getAttribute("user");
        final ModelAndView mav = new ModelAndView("/user");
        mav.addObject("showuser", user);
        return mav;
    }




}