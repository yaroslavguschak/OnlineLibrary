package com.github.yaroslavguschak.onlinelibrary.entityrequest;

import com.github.yaroslavguschak.onlinelibrary.entity.Permission;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "userReq")
public class RegisterRequest {

    private String login          = "";
    private String firstName      = "";
    private String lastName       = "";
    private String email          = "";
    private Permission permission = Permission.GUEST;
    private String password       = "";


    public RegisterRequest() {
    }

    public RegisterRequest(String login, String firstName, String lastName, String email, Permission permission, String password) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.permission = permission;
        this.password = password;
    }

    public Permission getPermission() {
        return permission;
    }

    @XmlElement
    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public String getLogin() {
        return login;
    }

    @XmlElement
    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    @XmlElement
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @XmlElement
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }
}
