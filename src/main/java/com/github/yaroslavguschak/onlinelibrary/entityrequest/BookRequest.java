package com.github.yaroslavguschak.onlinelibrary.entityrequest;

import com.github.yaroslavguschak.onlinelibrary.entity.Book;
import com.github.yaroslavguschak.onlinelibrary.entity.Genre;

public class BookRequest {

    private Long id = 0L;

    private String  isbn     = "";
    private String  author   = "";
    private String  title    = "";
    private Genre   genre    = Genre.OTHER;
    private int     year     = 0;
    private String  city     = "";
    private int     pages    = 0;
    private String  booktext = "";

    public BookRequest() {
    }

    public BookRequest(Book book) {
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.author = book.getAuthor();
        this.title = book.getTitle();
        this.genre = book.getGenre();
        this.year = book.getYear();
        this.city = book.getCity();
        this.pages = book.getPages();
        this.booktext = book.getBooktext();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getBooktext() {
        return booktext;
    }

    public void setBooktext(String booktext) {
        this.booktext = booktext;
    }
}
