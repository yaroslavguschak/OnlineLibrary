package com.github.yaroslavguschak.onlinelibrary.entity;

import com.github.yaroslavguschak.onlinelibrary.entityrequest.RegisterRequest;
import com.github.yaroslavguschak.onlinelibrary.util.CSHA1Util;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.security.GeneralSecurityException;


@Entity
@Table(name = "libuser")
@NamedQueries({ @NamedQuery(name = "User.getUserByLogin",     query = "SELECT u FROM User u where u.login = :userl"),
                @NamedQuery(name = "User.getCountByLogin",    query = "SELECT COUNT(u) FROM User u where u.login = :userl"),
                @NamedQuery(name = "User.getPassHashByLogin", query = "SELECT u.passwordhash FROM User u where u.login = :userl")})
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "login", unique = true, length = 30)
    private String login;

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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    Shelf shelf;




    public User() {
        this.login = "Anonymous";
        this.firstName = "Anonymous";
        this.lastName  = "Anonymous";
        this.email     = "Anonymous";
        this.passwordhash = "Anonymous";
        this.permission = Permission.GUEST;
        this.shelf = new Shelf();
    }

    public User(String login, String firstName, String lastName, String email, Permission permission, String password) throws GeneralSecurityException {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordhash = CSHA1Util.getSHA1String(password);
        this.permission = permission;
        this.shelf = new Shelf();
    }

    public void copyAllFields (User user) throws GeneralSecurityException {
        this.id = user.getId();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.passwordhash = user.getPasswordhash();
        this.permission = user.getPermission();
        this.shelf = user.getShelf();
    }

    public User(RegisterRequest registerRequest) throws GeneralSecurityException {
        this.login        = registerRequest.getLogin();
        this.firstName    = registerRequest.getFirstName();
        this.lastName     = registerRequest.getLastName();
        this.email        = registerRequest.getEmail();
        this.passwordhash = CSHA1Util.getSHA1String(registerRequest.getPassword());
        this.permission   = registerRequest.getPermission();
        this.shelf        = new Shelf();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }


    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }




    @Override
    public String toString() {
        return '\n'  +  "===== USER ID: " + id + " ======================================" + '\n' +
                        "   login:        " + login +  '\n'+
                        "   firstName:    " + firstName + '\n' +
                        "   lastName:     " + lastName + '\n' +
                        "   email:        " + email + '\n' +
                        "   permission:   " + permission + '\n' +
                        "   passwordhash: " + passwordhash + '\n' +
                        "=======================================================";
    }
}

