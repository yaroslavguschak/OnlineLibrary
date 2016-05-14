package com.github.yaroslavguschak.onlinelibrary.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.yaroslavguschak.onlinelibrary.dao.BookDAO;
import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.util.FileOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PdfDownloadController {
    @Autowired
    BookDAO bookDAO;

    @Autowired
    ServletContext context;

    @RequestMapping(value="/download/{bookIdDotPDF}", method = RequestMethod.GET)
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("bookIdDotPDF") String bookIdDotPDF) throws IOException {

        String stringBookID = bookIdDotPDF.substring(0, bookIdDotPDF.length() - 4); //cut extension

        Book book = bookDAO.getBookById(Long.parseLong(stringBookID));




//        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");

        String filePath= "\\resources\\downloads\\book"+ book.getId() + ".pdf";
        String fullPath = appPath + filePath;
//        File pdfFile = new File(filePath);
//        pdfFile.createNewFile();
        File file = null;
        file = new File(fullPath);
        FileOperator.saveBytesToFile(file, book.getPdf());


//        if(bookIdDotPDF.equalsIgnoreCase("book25.pdf")){
        System.out.println("Prepare file for downloading: " + filePath);

//        }
//        else{
//        }

        if(!file.exists()){
            String errorMessage = "Sorry. The file you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }

        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }

        System.out.println("mimetype : " + mimeType);

        response.setContentType(mimeType);

        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));


        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

        response.setContentLength((int)file.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
        file.delete();
    }

}