package com.github.yaroslavguschak.onlinelibrary.entityrequest;

import com.github.yaroslavguschak.onlinelibrary.entity.Permission;

public class RegisterRequest {

    private String login = "";
    private String firstName      = "";
    private String lastName       = "";
    private String email          = "";
    private String password       = "";
    private Permission permission = Permission.GUEST;

    public RegisterRequest() {
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
