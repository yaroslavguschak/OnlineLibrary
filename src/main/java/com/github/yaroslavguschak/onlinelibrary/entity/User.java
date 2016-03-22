package com.github.yaroslavguschak.onlinelibrary.entity;

import com.github.yaroslavguschak.onlinelibrary.util.CSHA1Util;

import javax.persistence.*;
import java.security.GeneralSecurityException;

@Entity
@Table(name = "libuser")
public class User {

    @Id
    @GeneratedValue(generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "SC_SEQ", allocationSize = 1)
    private Integer Id;

    @Column(name = "nickname", length = 30)
    private String nickname;

    @Column(name = "firstName", length = 100)
    private String firstName;

    @Column(name = "lastName", length = 100)
    private String lastName;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "passwordhash", length = 64)
    private String passwordhash;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    private Permission permission;

    /// need add Class Shelf




    public User() {
        this.nickname  = "0nickname";
        this.firstName = "0first name";
        this.lastName  = "0last name";
        this.email     = "0email";
        this.passwordhash = "notgen";
        this.permission = Permission.GUEST;
    }

    public User(String nickname, String firstName, String lastName, String email, Permission permission, String password) throws GeneralSecurityException {
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordhash = CSHA1Util.getSHA1String(password);
        this.permission = permission;

    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }


    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", permission=" + permission +
                '}';
    }
}

