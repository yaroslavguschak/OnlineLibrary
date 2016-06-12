package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Permission;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.BookRequest;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.LoginRequest;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.SearchRequest;
import com.github.yaroslavguschak.onlinelibrary.util.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.*;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.io.ByteStreams;


import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;

/** create, edit books*/
@Controller
public class BookController {
    @Autowired
    BookDAO bookDAO;

    @Autowired
    ServletContext context;

    @Inject
    User user;

    @Inject
    Alert alert;

    @Autowired
    DateFormat dateFormat;

    /* Get book title img */
    @RequestMapping(value = "/img/{bookId}")
    public ResponseEntity<byte[]> getuseravatar(@PathVariable String bookId) {
        final String noPhoto = "/resources/images/NoImageAvailable.jpg";
        final HttpHeaders headers = new HttpHeaders();
        byte[] result = bookDAO.getBookById(Long.valueOf(bookId)).getImg();
        if (result != null) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        } else {
            headers.setContentType(MediaType.IMAGE_PNG);
            try {
                result = ByteStreams.toByteArray(context.getResourceAsStream(noPhoto));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        headers.setContentLength(result.length);
        return new ResponseEntity<byte[]> (result, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/newbook")
    public ModelAndView registerGet() {
        final ModelAndView mav = new ModelAndView("/newbook");
        BookRequest bookRequest = new BookRequest();
        mav.addObject("bookRequest",bookRequest);
        return mav;
    }

    @RequestMapping(value = "/doaddbook", method = RequestMethod.POST)
    @ResponseBody
    public void addBook(@ModelAttribute("bookRequest") BookRequest bookReq, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
            HttpSession httpSession = req.getSession(true);

            Book book = new Book(bookReq.getAuthor(),
                                 bookReq.getTitle(),
                                 bookReq.getAnnotation(),
                                 bookReq.getGenre(),
                                 bookReq.getYear(),
                                 bookReq.getCity(),
                                 bookReq.getIsbn(),
                                 bookReq.getPages(),
                                 bookReq.getBooktext()   );

            bookDAO.saveNewBook(book);
            httpSession.setAttribute("book", book); // це тут потрібно?????
            RequestDispatcher requestDispatcher;
            requestDispatcher = req.getRequestDispatcher("/newbook");
            requestDispatcher.forward(req, resp);

    }

    @RequestMapping(value = "/doeditbook", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView editActionBook(@ModelAttribute("bookRequest") BookRequest bookReq, HttpServletRequest req,
                                       HttpServletResponse resp,  @RequestParam("cover") MultipartFile cover)
            throws ServletException, IOException {
        Book book = bookDAO.getBookById(bookReq.getId());
        if (cover.isEmpty()){
            bookReq.setImg(book.getImg());
        } else {
            bookReq.setImg(cover.getBytes());
        }
        book.updateFromRequest(bookReq);
        bookDAO.updateBook(book);
        return new ModelAndView("redirect:/index");

    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    public ModelAndView viewDetailedBook(@PathVariable String bookId) {
        final ModelAndView mav = new ModelAndView("book");
        mav.addObject("showuser", user);

        if (user.getPermission() != Permission.GUEST) {
            Book book = bookDAO.getBookById(Long.parseLong(bookId));
            mav.addObject("book", book);
            mav.addObject("searchRequest", new SearchRequest());
        } else {
            mav.addObject("loginRequest", new LoginRequest());
        }
        return mav;
    }
}