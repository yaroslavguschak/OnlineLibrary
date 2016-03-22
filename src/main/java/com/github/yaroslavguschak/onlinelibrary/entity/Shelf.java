package com.github.yaroslavguschak.onlinelibrary.entity;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
    private List <Book> bookList = new ArrayList<>();

    public Shelf() {
    }

    public Shelf(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    //specific methods

    public List<Book> getBookListByTitle (String searchInput){
        List<Book> matchByTitleList = new ArrayList<>();

        for (Book book : this.bookList) {
            if (book.getTitle().toLowerCase().contains( searchInput.toLowerCase() )){
                    matchByTitleList.add(book);
            }
        }
    return matchByTitleList;
    }

    public List<Book> getBookListByAuthor (String searchInput){
        List<Book> matchByAuthorList = new ArrayList<>();

        for (Book book : this.bookList) {
            if (book.getAuthor().toLowerCase().contains( searchInput.toLowerCase() )){
                matchByAuthorList.add(book);
            }
        }
        return matchByAuthorList;
    }


    public List<Book> getBookListByGenre (String searchInput){
        List<Book> matchByGenreList = new ArrayList<>();

        for (Book book : this.bookList) {
            if (book.getGenre().toString().toLowerCase().contains( searchInput.toLowerCase() )){
                matchByGenreList.add(book);
            }
        }
        return matchByGenreList;
    }

    public void addBook (Book book){
        this.bookList.add(book);
    }

    public void delById(int bookId){
        this.bookList.remove(bookId);
    }







}
