package com.github.yaroslavguschak.onlinelibrary.entity;

import javax.persistence.*;

@Entity
@Table(name = "book")
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

    public Book() {
        this.author = "no_author";
        this.title  = "no_title";
        this.genre  = Genre.OTHER;
        this.year   = 0;
        this.city   = "no_city";
        this.isbn = "no_ISBN";
        this.pages  = 0;
        this.booktext = "very long tale about there are not anything";
    }


    public Book(String author, String title, Genre genre, int year, String city, String isbn, int pages, String booktext) {
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.city = city;
        this.isbn = isbn;
        this.pages = pages;
        this.booktext = booktext;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String ISBN) {
        this.isbn = ISBN;
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

    public void setBooktext(String text) {
        this.booktext = text;
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
        return "Book{" +
                "ISBN='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", genre=" + genre +
                ", year=" + year +
                ", city='" + city + '\'' +
                ", pages=" + pages +
                ", booktext='" + booktext + '\'' +
                '}';
    }
}
