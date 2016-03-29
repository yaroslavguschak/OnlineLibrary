package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.entity.User;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.LoginRequest;
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

@Controller
public class LoginController {


    @RequestMapping(value = "/loginchek", method= RequestMethod.POST)
    @ResponseBody
    public void loginCheck(@ModelAttribute("loginRequest") LoginRequest loginRequest, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = "Yar";
        String password = "111";

        if (loginRequest.getLogin().equals(name)&&loginRequest.getPassword().equals(password)){
            HttpSession httpSession = req.getSession(true);
            System.out.println("User logged successful: " + name + " in time" + httpSession.getCreationTime());
            User user = new User();
            httpSession.setAttribute("user", user);
            RequestDispatcher requestDispatcher;
            requestDispatcher = req.getRequestDispatcher("/news");
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



}