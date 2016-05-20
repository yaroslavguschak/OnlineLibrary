package com.github.yaroslavguschak.onlinelibrary.util;

/**
 * Created by yars on 19.05.2016.
 */
public class Alert {
    private String message;

    public Alert() {
        message = "";
    }

    public Alert(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
