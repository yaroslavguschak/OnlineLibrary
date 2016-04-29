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
        user = userDAO.getUserById(user.getId());

        final ModelAndView mav = new ModelAndView("/shelf");

        System.out.println("****************WHAT IS IN A USER????**********************************");
        System.out.println(user.getShelf().getBookList().size());
        System.out.println("***********************************END**********************************");

        if (user != null){
            httpSession.setAttribute("bookList", user.getShelf().getBookList());
            mav.addObject("showuser",user);

//            List<Book> articleList = user.getShelf().getBookList();
//            httpSession.setAttribute("articleList", articleList);
//            mav.addObject("articleList",articleList);
            return mav;
        } else  {
            return mav;
        }
    }


    @RequestMapping(value = "/shelfaction" , method= RequestMethod.POST)
    public void archiveAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);
        User user = (User)httpSession.getAttribute("user");
        user = userDAO.getUserById(user.getId());

        List<Book> bookListFromView = (List<Book>) httpSession.getAttribute("bookList");

        if (req.getParameter("action").equals("SAVE")){
            System.out.print("LOG: " + "saving items...");
//            List<Book> bookList = (ArrayList<Book>)httpSession.getAttribute("bookList");
//            List<Book> bookListTEMP = new ArrayList<Book>();

            for (int i = 0; i < bookListFromView.size(); ++i ) {
                if (req.getParameter(String.valueOf(i))!= null && req.getParameter(String.valueOf(i)).equals("save")){
//                    bookListTEMP.add(bookList.get(i));
                    user.getShelf().addBook(bookListFromView.get(i));
                }
            }

//            bookDAO.addBooksToLibrary(bookListTEMP);
            userDAO.updateUser(user);
            System.out.println("LOG: " + "saving books to shelf... DONE!");
            RequestDispatcher requestDispatcher;
            requestDispatcher = req.getRequestDispatcher("/library");
            requestDispatcher.forward(req, resp);
        }

        if (req.getParameter("action").equals("DELETE")){

//            HttpSession httpSession = req.getSession(true);

            System.out.println("===============");
            System.out.println("************************************" + user.toString());


            for (int i = 0; i < bookListFromView.size(); ++i ) {
                if (req.getParameter(String.valueOf(i))!= null && req.getParameter(String.valueOf(i)).equals("delete")){
                    user.getShelf().delFromShelf(bookListFromView.get(i));
                }
                System.out.println("<>" +  i  + " " + req.getParameter( String.valueOf(i) ));
            }


            System.out.println("/-/-/*-/-/-/-/-/*-/-/-/*-/*-/" + user);
            userDAO.updateUser(user);
//            httpSession.setAttribute("user", user);
            System.out.println("****************WHAT IS IN A SESSION????**********************************");
            System.out.println(user.getShelf().getBookList().size());
            System.out.println("***********************************END**********************************");


            System.out.println("LOG: " + "deleting items... DONE!");
//            userDAO.saveDefoltUser();
            RequestDispatcher requestDispatcher;
            requestDispatcher = req.getRequestDispatcher("/shelf");
            requestDispatcher.forward(req, resp);
        }
    }
}




