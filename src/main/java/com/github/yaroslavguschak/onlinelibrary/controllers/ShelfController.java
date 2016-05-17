package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.dao.UserDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Permission;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.BookRequest;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.LoginRequest;
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
public class ShelfController {

    @Autowired
    BookDAO bookDAO;

    @Autowired
    UserDAO userDAO;

    @RequestMapping(value = "/shelf")
    public ModelAndView showShelf(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession(true);
        User user = (User)httpSession.getAttribute("user");
        final ModelAndView mav = new ModelAndView("/shelf");

        if (user != null){
            user = userDAO.getUserById(user.getId());
            mav.addObject("bookList", user.getShelf().getBookList());
            mav.addObject("showuser",user);
            return mav;
        } else  {
            mav.addObject("loginRequest",new LoginRequest());
            return mav;
        }
    }

    @RequestMapping(value = "/shelfaction" , method = RequestMethod.POST)
    public ModelAndView adminAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);
        User user = (User)httpSession.getAttribute("user");

        if(user != null & user.getPermission() == Permission.ADMIN) { // of course, view doesn't generate form for non-Admin/Subs user. Do for security.
            user = userDAO.getUserById(user.getId()); // check if another user with  ADMIN perm. del/edit some book(s)
            httpSession.setAttribute("user", user); // put update user in session
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
            return new ModelAndView("redirect:/shelf");
        } else {
            return new ModelAndView("redirect:/shelf");
        }
    }
}




