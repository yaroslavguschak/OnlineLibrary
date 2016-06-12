package com.github.yaroslavguschak.onlinelibrary.entity;


public enum Genre {
    NONE ("not selected"),
    COMEDY ("Comedy"),
    DRAMA("Drama"),
    NON_FICTION ("Non-fiction"),
    REALISTIC_FICTION ("Realistic fiction"),
    ROMANCE_NOVEL ("Romance novel"),
    SATIRE ("Satire"),
    TRAGEDY ("Tragedy"),
    TRAGICOMEDY ("Tragicomedy"),
    HORROR ("Horror"),
    OTHER ("Other");

    private final String displayGenre;

    Genre (String displayGenre) {
        this.displayGenre = displayGenre;
    }

    public String getDisplayGenre() {
        return displayGenre;
    }
}
