package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.dao.UserDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShelfController {

    @Autowired
    BookDAO dao;
    UserDAO userDAO;


    @RequestMapping(value = "/shelf")
    public ModelAndView showShelf(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession(true);
        User user = (User)httpSession.getAttribute("user");
        final ModelAndView mav = new ModelAndView("/shelf");

        if (user != null){
            mav.addObject("showuser",user);
            ArrayList<Book> articleList = (ArrayList<Book>) dao.getAllUser();
            httpSession.setAttribute("articleList", articleList);
            mav.addObject("articleList",articleList);
            return mav;
        } else  {
            return mav;
        }
    }


    @RequestMapping(value = "/shelfaction" , method= RequestMethod.POST)
    public void archiveAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);

        if (req.getParameter("action").equals("SAVE")){
            System.out.print("LOG: " + "saving items...");
            List<Book> bookList = (ArrayList<Book>)httpSession.getAttribute("bookList");
            List<Book> bookListTEMP = new ArrayList<Book>();

            for (int i = 0; i < bookList.size(); ++i ) {
                if (req.getParameter(String.valueOf(i))!= null && req.getParameter(String.valueOf(i)).equals("save")){
                    bookListTEMP.add(bookList.get(i));
                }
            }
            dao.addBooksToLibrary(bookListTEMP);
            System.out.println(" DONE!");
            RequestDispatcher requestDispatcher;
            requestDispatcher = req.getRequestDispatcher("/news");
            requestDispatcher.forward(req, resp);
        }

        if (req.getParameter("action").equals("DELETE")){
            System.out.println("LOG: " + "deleting items... DONE!");
//            userDAO.saveDefoltUser();
            RequestDispatcher requestDispatcher;
            requestDispatcher = req.getRequestDispatcher("/archive");
            requestDispatcher.forward(req, resp);
        }
    }
}




