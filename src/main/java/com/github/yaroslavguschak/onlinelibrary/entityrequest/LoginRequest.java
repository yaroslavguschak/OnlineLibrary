package com.github.yaroslavguschak.onlinelibrary.entityrequest;

public class LoginRequest {
    private String name;
    private String password;

    public LoginRequest() {
        this.name = "your nick";
        this.password = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
