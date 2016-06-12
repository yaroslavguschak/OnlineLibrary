package com.github.yaroslavguschak.onlinelibrary.entity;

import com.github.yaroslavguschak.onlinelibrary.entityrequest.BookRequest;
import com.github.yaroslavguschak.onlinelibrary.util.PDFconverter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.IOException;

@XmlRootElement(name = "book")
@Entity
@Table(name = "book")
@NamedQueries({ @NamedQuery(name = "Book.getBookById",    query = "SELECT b FROM Book b WHERE b.id = :bookid"),
                @NamedQuery(name = "Book.searchBookByTitleAndAuthor",
                        query = "SELECT b FROM Book b WHERE lower (b.title)  LIKE lower(:search) " +
                                                        "OR lower (b.author) LIKE lower(:search)"),
                @NamedQuery(name = "Book.searchBookByTitleAndAuthorAndGenre",
                        query = "SELECT b FROM Book b WHERE ( lower (b.title)  LIKE lower(:search) " +
                                "OR lower (b.author) LIKE lower(:search) ) AND b.genre = :genre")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "isbn", length = 30)
    private String isbn;

    @Column(name = "author", length = 100)
    private String  author;

    @Column(name = "title", length = 100)
    private String  title;

    @Column(name = "annotation",columnDefinition="text")
    private String annotation;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre   genre;

    @Column(name = "year")
    private int     year;

    @Column(name = "city", length = 50)
    private String  city;

    @Column(name = "pages")
    private int     pages;

    @Column(name = "booktext",columnDefinition="text")
    private String booktext;


    @Column(name = "pdf")
    private byte[] pdf;


    @Column(name = "img")
    private byte[] img;


    public Book() {
        this.author = "no_author";
        this.title  = "no_title";
        this.annotation = "no_annotation";
        this.genre  = Genre.OTHER;
        this.year   = 0;
        this.city   = "no_city";
        this.isbn = "no_ISBN";
        this.pages  = 0;
        this.booktext = "very long tale about there are not anything";
    }


    public Book(String author, String title, String annotation, Genre genre, int year, String city, String isbn, int pages, String booktext) {
        this.author = author;
        this.title = title;
        this.annotation = annotation;
        this.genre = genre;
        this.year = year;
        this.city = city;
        this.isbn = isbn;
        this.pages = pages;
        this.booktext = booktext;
        try {
            this.pdf = PDFconverter.convertToPdfByte(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Book updateFromRequest (BookRequest bookRequest){
        this.author = bookRequest.getAuthor();
        this.title = bookRequest.getTitle();
        this.annotation = bookRequest.getAnnotation();
        this.genre = bookRequest.getGenre();
        this.year = bookRequest.getYear();
        this.city = bookRequest.getCity();
        this.isbn = bookRequest.getIsbn();
        this.pages = bookRequest.getPages();
        this.booktext = bookRequest.getBooktext();
        try {
            this.pdf = PDFconverter.convertToPdfByte(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.img = bookRequest.getImg();
        return this;
    }

    public void pdfCreate(){
        try {
            this.pdf = PDFconverter.convertToPdfByte(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Long getId() {
        return id;
    }

    @XmlTransient
    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }
    @XmlElement
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnotation() {
        return annotation;
    }

    @XmlElement
    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Genre getGenre() {
        return genre;
    }

    @XmlElement
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    @XmlElement
    public void setYear(int year) {
        this.year = year;
    }

    public String getCity() {
        return city;
    }

    @XmlElement
    public void setCity(String city) {
        this.city = city;
    }

    public String getIsbn() {
        return isbn;
    }

    @XmlElement
    public void setIsbn(String ISBN) {
        this.isbn = ISBN;
    }

    public int getPages() {
        return pages;
    }

    @XmlElement
    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getBooktext() {
        return booktext;
    }

    @XmlElement
    public void setBooktext(String text) {
        this.booktext = text;
    }


    public byte[] getPdf() {
        return pdf;
    }

    @XmlTransient
    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public byte[] getImg() {
        return img;
    }

    @XmlTransient
    public void setImg(byte[] img) {
        this.img = img;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (year != book.year) return false;
        if (pages != book.pages) return false;
        if (!author.equals(book.author)) return false;
        if (!title.equals(book.title)) return false;
        if (genre != book.genre) return false;
        if (!city.equals(book.city)) return false;
        if (!isbn.equals(book.isbn)) return false;
        return booktext.equals(book.booktext);

    }

    @Override
    public int hashCode() {
        int result = author.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + genre.hashCode();
        result = 31 * result + year;
        result = 31 * result + city.hashCode();
        result = 31 * result + isbn.hashCode();
        result = 31 * result + pages;
        result = 31 * result + booktext.hashCode();
        return result;
    }

    @Override
    public String toString() {

        int limitAnnotText = 50;
        int limitBookText = 100;

        return '\n' + "===== BOOK ID: " + id + " ======================================" + '\n' +
                "   title:        " + title +  '\n'+
                "   author:       " + author + '\n' +
                "   genre:        " + genre + '\n' +
                "   year:         " + year + '\n' +
                "   city:         " + city + '\n' +
                "   pages:         " + pages + '\n' +
                "   annotation:   " + (annotation.codePointCount(0, annotation.length()) > limitAnnotText ?
                                       annotation.substring(0, annotation.offsetByCodePoints(0, limitAnnotText)) : annotation) + '\n' +
                "   booktext:     " + (booktext.codePointCount(0, booktext.length()) > limitBookText ?
                                       booktext.substring(0, booktext.offsetByCodePoints(0, limitBookText)) : booktext) + '\n' +
                "=======================================================";
    }
}
