package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.entity.Genre;
import com.github.yaroslavguschak.onlinelibrary.entity.Permission;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.LoginRequest;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.SearchRequest;
import com.github.yaroslavguschak.onlinelibrary.util.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.text.DateFormat;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    BookDAO bookDAO;

    @Inject
    User user;

    @Inject
    Alert alert;

    @Autowired
    DateFormat dateFormat;

    @RequestMapping(value = "/search")
    public ModelAndView showIndex(@ModelAttribute("searchRequest")SearchRequest searchRequest)  {
        final ModelAndView mav = new ModelAndView("/search");
        mav.addObject("showuser", user);

        String matchesCount;
        if (user.getPermission() != Permission.GUEST) {
            List <Book> searchedBooks = bookDAO.searchBooks(searchRequest);
            mav.addObject("bookList", searchedBooks);
            mav.addObject("searchRequest", searchRequest);
            matchesCount = "We have " + searchedBooks.size() + " matches";
        } else {
            mav.addObject("loginRequest", new LoginRequest());
            matchesCount = "Please, login and use search ;)";
        }

        mav.addObject("matchesCount", matchesCount);
        return mav;
    }
}
