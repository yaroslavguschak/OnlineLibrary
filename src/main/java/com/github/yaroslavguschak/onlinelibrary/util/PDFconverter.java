package com.github.yaroslavguschak.onlinelibrary.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
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
            PDPageContentStream contentStream = new PDPageContentStream(doc, page);

            File fontFile = new File("fonts\\serif.ttf");
            PDType0Font font = PDType0Font.load(doc, fontFile);
            PDFont pdfFont = font;
//            PDFont pdfFont = PDType1Font.TIMES_ROMAN;
            float fontSize = 10;
            float leading = 1.5f * fontSize;

            PDRectangle mediabox = page.getMediaBox();
            float margin = 72;
            float width = mediabox.getWidth() - 2*margin;
            float height = (mediabox.getHeight() - 2*margin);
            float startX = mediabox.getLowerLeftX() + margin;
            float startY = mediabox.getUpperRightY() - margin;

            String text = book.getBooktext();
            text = text.replace("" + (char)13, "");
            text = text.replace('\n','_');
            text = text.replace('\t',' ');

            List<String> lines = new ArrayList<String>();
            int lastSpace = -1;
            while (text.length() > 0)
            {
                int spaceIndex = text.indexOf(' ', lastSpace + 1);
                if (spaceIndex < 0){
                    spaceIndex = text.length();}

                String subString = text.substring(0, spaceIndex);
                float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
                if (size > width)
                {
                    if (lastSpace < 0)
                        lastSpace = spaceIndex;
                    subString = text.substring(0, lastSpace);
                    lines.add(subString);
                    text = text.substring(lastSpace).trim();
                    lastSpace = -1;
                }
                else if (spaceIndex == text.length()){
                    lines.add(text);
                    text = "";}
                    else { lastSpace = spaceIndex;
                    }
            }

            int maxLine = (int) (height / leading);
            int lineCount = 0;
            int pageCount = 1;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, fontSize);
            contentStream.newLineAtOffset(startX, startY);

            //**printing book title, author, isbn...*

            contentStream.showText(book.getTitle());
            contentStream.newLineAtOffset(0, -leading);
            ++lineCount;

            contentStream.showText(book.getAuthor());
            contentStream.newLineAtOffset(0, -leading);
            ++lineCount;

            contentStream.showText(book.getGenre().getDisplayGenre());
            contentStream.newLineAtOffset(0, -leading);
            ++lineCount;

            contentStream.showText(book.getCity() + ", " + book.getYear());
            contentStream.newLineAtOffset(0, -leading);
            ++lineCount;

            contentStream.setFont(pdfFont, fontSize);
//            contentStream.newLineAtOffset(startX, startY);

            for (String line: lines) {
                if (lineCount < maxLine){
                    ++lineCount;
                } else {
                    //**printing a page number *
                    contentStream.setFont(PDType1Font.TIMES_BOLD, 15);
                    contentStream.newLineAtOffset(200, -leading);
                    contentStream.showText("page " + pageCount);
                    contentStream.endText();
                    contentStream.close();

                    //**creating a new page, set font and position
                    page = new PDPage();
                    doc.addPage(page);
                    contentStream = new PDPageContentStream(doc,page);
                    contentStream.beginText();
                    contentStream.setFont(pdfFont, fontSize);
                    contentStream.newLineAtOffset(startX, startY);
                    lineCount = 0;
                    ++pageCount;
                }
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -leading);
            }
            contentStream.endText();
            contentStream.close();


            book.setPages(pageCount);
            doc.save(fileName);
            doc.close();
        }
        catch(IOException  e){
            System.out.println(e.getMessage());
        }
        return pdfFile;
    }




    public static byte[] convertToPdfByte(Book book) throws IOException {
        return FileOperator.readBytesFromFileAndDel(PDFconverter.convertToPDF(book));

    }

}
