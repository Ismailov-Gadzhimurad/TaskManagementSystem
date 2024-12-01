package com.testapplication.desktop.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String title, description, status, priority, comment, author, executor;

    public Task(String title, String description, String status, String priority, String comment, String author, String executor) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.comment = comment;
        this.author = author;
        this.executor = executor;
    }
}
