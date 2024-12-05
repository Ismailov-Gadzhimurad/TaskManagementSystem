package com.testapplication.desktop.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class MyUser {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Getter
    @Column(unique = true)
    private String username, password, roles;


    public MyUser(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

}


