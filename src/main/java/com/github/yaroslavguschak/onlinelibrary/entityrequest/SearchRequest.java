package com.github.yaroslavguschak.onlinelibrary.entityrequest;

import com.github.yaroslavguschak.onlinelibrary.entity.Genre;

public class SearchRequest {


    private String searchTextInput = "Author or title";

    private Genre   genre    = Genre.NONE;

    public SearchRequest() {
    }

    public String getSearchTextInput() {
        return searchTextInput;
    }

    public void setSearchTextInput(String searchTextInput) {
        this.searchTextInput = searchTextInput;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

}
