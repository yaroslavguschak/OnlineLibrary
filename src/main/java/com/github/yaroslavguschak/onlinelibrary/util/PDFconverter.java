package com.github.yaroslavguschak.onlinelibrary.util;

import java.io.File;
import java.io.IOException;

import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PDFconverter {

    private PDFconverter(){
    }

    public static File convertToPDF(Book book) {
        String fileName = "temp\\book_"+ book.getId() + "_" + book.getIsbn() + ".pdf"; // name of our file
        File pdfFile = new File(fileName);

        try{

            System.out.println("Create PDF file from Book " + book.toString() + "...");


            PDDocument doc = new PDDocument(); //IOC
            PDPage page = new PDPage();        //IOC

            doc.addPage(page);

            PDPageContentStream content = new PDPageContentStream(doc, page);

            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 26);
            content.newLineAtOffset(220, 750);
            content.showText(book.getTitle());
            content.endText();


            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 16);
            content.newLineAtOffset(80, 700);
            content.showText("isbn : " + book.getIsbn());
            content.endText();


            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 16);
            content.newLineAtOffset(80,650);
            content.showText("Father Name : ");
            content.endText();

            content.beginText();
            content.newLineAtOffset(80,600);
            content.showText("DOB : ");
            content.endText();


            content.close();
            doc.save(pdfFile);
            doc.close();

            System.out.println("your file created in : "+ System.getProperty("user.dir"));

        }
        catch(IOException  e){
            System.out.println(e.getMessage());
        }
        return pdfFile;
    }




    public static byte[] convertToByte(Book book) throws IOException {
        return FileOperator.readBytesFromFile(PDFconverter.convertToPDF(book));

    }

}
