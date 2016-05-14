package com.github.yaroslavguschak.onlinelibrary.controllers;

import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.dao.UserDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.entity.Permission;
import com.github.yaroslavguschak.onlinelibrary.entity.User;
import com.github.yaroslavguschak.onlinelibrary.entityrequest.RegisterRequest;
import com.github.yaroslavguschak.onlinelibrary.util.FileOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class DemoController {


    @Autowired
    BookDAO bookDAO;

    @Autowired
    UserDAO userDAO;

    @RequestMapping(value = "/demo")
    @ResponseBody
    public String test() throws GeneralSecurityException, IOException, JAXBException {
        return  fillDB();
    }



    private String fillDB() throws GeneralSecurityException, IOException, JAXBException {


        JAXBContext jaxbBookContext = JAXBContext.newInstance(Book.class);
        Unmarshaller unmarshallerBook = jaxbBookContext.createUnmarshaller();

        Book tempBook;
        final File bookFolder = new File("temp\\books");
        final File coverFolder = new File("temp\\books\\covers");
        for (final File xmlFile : bookFolder.listFiles()) {

            if (xmlFile.isDirectory()){

            } else {
                System.out.println("READ BOOK XML FILE: " + xmlFile.getAbsolutePath());
                tempBook = (Book) unmarshallerBook.unmarshal(xmlFile);
                String imgName = xmlFile.getName().substring(5,xmlFile.getName().length()-4);
                File coverImgFile = coverFolder.listFiles()[Integer.parseInt(imgName) - 1];
                tempBook.setImg(FileOperator.readBytesFromFile(coverImgFile));
                tempBook.pdfCreate();
                bookDAO.saveNewBook(tempBook);
            }
        }

        List<RegisterRequest> registerRequestList = new ArrayList<>();
        JAXBContext jaxbUserContext = JAXBContext.newInstance(RegisterRequest.class);
        Unmarshaller unmarshallerReqUser = jaxbUserContext.createUnmarshaller();

        RegisterRequest tempRegisterRequest;
        final File userFolder = new File("temp\\users");
        for (final File xmlFile : userFolder.listFiles()) {
            System.out.println("READ USER XML FILE: " + xmlFile.getAbsolutePath().toUpperCase());
            tempRegisterRequest = (RegisterRequest) unmarshallerReqUser.unmarshal(xmlFile);
            User tempUser = new User(tempRegisterRequest);
            userDAO.saveNewUser(tempUser);
        }

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Wellcome in test controller");
        stringBuffer.append("\n From XMl files created and stored to DB:\n\n     Users:");
        List<User> userList = userDAO.getAllUser();
        for (User u : userList) {
            stringBuffer.append(u.toString());
        }

        stringBuffer.append("\n\n\n  Books:");
        List<Book> bookList = bookDAO.getAllBooks();
        for (Book b : bookList) {
            stringBuffer.append(b.toString());
        }

        return "<pre>" + stringBuffer.toString() + "</pre>";
    }
}
