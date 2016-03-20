package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.entity.DAO;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.BookRequest;
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

@Controller
public class BookController {
    @Autowired
    DAO dao;


    @RequestMapping(value = "/doaddbook", method = RequestMethod.POST)
    @ResponseBody
    public void doRegister(@ModelAttribute("bookRequest") BookRequest bookReq, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
            HttpSession httpSession = req.getSession(true);

            Book book = new Book(bookReq.getAuthor(),
                                 bookReq.getTitle(),
                                 bookReq.getGenre(),
                                 bookReq.getYear(),
                                 bookReq.getCity(),
                                 bookReq.getIsbn(),
                                 bookReq.getPages(),
                                 bookReq.getBooktext()   );

            System.out.println("User CREATED successful: " + book + " in time" + httpSession.getCreationTime());
            dao.saveNewBook(book);
            httpSession.setAttribute("book", book); // це тут потрібно?????
            RequestDispatcher requestDispatcher;
            requestDispatcher = req.getRequestDispatcher("/newbook");
            requestDispatcher.forward(req, resp);

    }

    @RequestMapping(value = "/newbook")
    public ModelAndView registerGet() {
        final ModelAndView mav = new ModelAndView("/newbook");
        BookRequest bookRequest = new BookRequest();
        mav.addObject("bookRequest",bookRequest);
        return mav;
    }



}