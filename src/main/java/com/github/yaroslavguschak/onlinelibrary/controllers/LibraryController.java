package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LibraryController {

    @Autowired
    BookDAO bookDAO;



    @RequestMapping(value = "/books")
    public ModelAndView showNews(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession(true);


//        user = (User)httpSession.getAttribute("user");
//        List<Book> bookList = bookDAO.getAllUser();
//        List<Integer> isSaved = new ArrayList<>(bookList.size());//????????
        final ModelAndView mav = new ModelAndView("/news");

        //================
//        bookDAO.saveDefoltUser(); ////заглушка
        ////========

//        if (user != null){
//            ArrayList<Book> booksSavedOnShelf = (ArrayList<Book>) bookDAO.getAllUser();
//
//            for (int i = 0; i < bookList.size(); ++i) {
//                isSaved.add(i,-100);
//                for (Book articleSaved:booksSavedOnShelf) {
//                    if (bookList.get(i).getTitle().equals(articleSaved.getTitle())){
//                        isSaved.add(i,100);
//                    }
//                }
//            }
//            mav.addObject("showuser", user);
//            httpSession.setAttribute("bookList", bookList);
//            mav.addObject("bookList", bookList);
//            mav.addObject("isSaved", isSaved);
//            return mav;
//        } else  {
//            mav.addObject("bookList", bookList);
            return mav;
//        }
    }




}
