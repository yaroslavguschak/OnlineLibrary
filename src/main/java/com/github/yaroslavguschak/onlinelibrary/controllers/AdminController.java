package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.entity.Permission;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    BookDAO bookDAO;

    @RequestMapping(value = "/admin")
    public ModelAndView showNews(HttpServletRequest req, HttpServletResponse resp) {
        final ModelAndView mav = new ModelAndView("/admin");
        HttpSession httpSession = req.getSession(true);
        User user = (User)httpSession.getAttribute("user");

        if (user != null && user.getPermission().equals(Permission.ADMIN)){
            List<Book> allBooksInLibrary = bookDAO.getAllBooks();
            mav.addObject("showuser", user);
            mav.addObject("bookList", allBooksInLibrary);
            return mav;
        } else  {
            mav.addObject("showuser", user);
            return mav;
        }
    }

}
