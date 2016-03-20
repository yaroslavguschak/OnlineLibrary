package com.github.yaroslavguschak.onlinelibrary.entityrequest;

import com.github.yaroslavguschak.onlinelibrary.entity.Genre;

public class BookRequest {

    private String isbn = "";
    private String  author   = "";
    private String  title    = "";
    private Genre   genre    = Genre.OTHER;
    private int     year     = 0;
    private String  city     = "";
    private int     pages    = 0;
    private String  booktext = "";

    public BookRequest() {
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
