package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.entity.DAO;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.RegisterRequest;
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
public class RegisterController {
    @Autowired
    DAO dao;


    @RequestMapping(value = "/doregister", method= RequestMethod.POST)
    @ResponseBody
    public void doRegister(@ModelAttribute("registerRequest") RegisterRequest regReq, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, GeneralSecurityException {

            HttpSession httpSession = req.getSession(true);

            User user = new User(regReq.getNickname(),regReq.getFirstName(), regReq.getLastName(), regReq.getEmail(), regReq.getPermission(), regReq.getPassword());
            System.out.println("User REGISTER successful: " + user + " in time" + httpSession.getCreationTime());
            dao.saveNewUser(user);
            httpSession.setAttribute("user", user);
            RequestDispatcher requestDispatcher;
            requestDispatcher = req.getRequestDispatcher("/register");
            requestDispatcher.forward(req, resp);

    }

    @RequestMapping(value = "/register")
    public ModelAndView registerGet() {
        final ModelAndView mav = new ModelAndView("/register");
        RegisterRequest registerRequest = new RegisterRequest();
        mav.addObject("registerRequest",registerRequest);
        return mav;
    }



}