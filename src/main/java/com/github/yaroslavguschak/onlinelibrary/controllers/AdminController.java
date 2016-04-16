package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.entity.Permission;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    BookDAO bookDAO;

    @RequestMapping(value = "/admin")
    public ModelAndView showNews(HttpServletRequest req) {
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


    @RequestMapping(value = "/adminaction" , method= RequestMethod.POST)
    public ModelAndView adminAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);
        User user = (User) httpSession.getAttribute("user");

        if (req.getParameter("action").equals("Edit")) {
            String stringBookId = req.getParameter("bookId");
            System.out.print("LOG: " + "user sent book # " + stringBookId + "for editing");

            Book book = bookDAO.getBookById(Long.valueOf(stringBookId));
            BookRequest bookRequest = new BookRequest(book);
            final ModelAndView mav = new ModelAndView("/editbook");

            mav.addObject("bookRequest", bookRequest);
            return mav;

//            userDAO.updateUser(user);
//            System.out.println("LOG: " + "saving books to shelf... DONE!");
//            RequestDispatcher requestDispatcher;
//            requestDispatcher = req.getRequestDispatcher("/library");
//            requestDispatcher.forward(req, resp);
        }

//
        return new ModelAndView("/editbook");
    }
}
