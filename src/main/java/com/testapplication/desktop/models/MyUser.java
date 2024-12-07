package com.testapplication.desktop.models;

import jakarta.persistence.*;

@Entity

@Table(name = "users")

public class MyUser {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username, password, roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public MyUser() {
    }

    public MyUser(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}


