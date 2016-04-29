package com.github.yaroslavguschak.onlinelibrary.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shelf")
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//cascade=CascadeType.ALL
    @OneToMany(fetch =   FetchType.EAGER)
    private List <Book> bookList = new ArrayList<>();


    public Shelf() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void delFromShelf(Book book){
        this.getBookList().remove(book);//////////

    }

    @Override
    public String toString() {
        return "Shelf{" +
                "id=" + id +
                ", bookList=" + bookList +
                '}';
    }
}
