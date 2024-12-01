package com.testapplication.desktop.dto;

import jakarta.validation.constraints.NotBlank;

public class TaskDTO {

    @NotBlank
    public String title;
    @NotBlank
    public String description;
    @NotBlank
    public String status;
    @NotBlank
    public String priority;
    @NotBlank
    public String comment;
    @NotBlank
    public String author;
    @NotBlank
    public String executor;


    public @NotBlank String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }

    public @NotBlank String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }

    public @NotBlank String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank String status) {
        this.status = status;
    }

    public @NotBlank String getPriority() {
        return priority;
    }

    public void setPriority(@NotBlank String priority) {
        this.priority = priority;
    }

    public @NotBlank String getComment() {
        return comment;
    }

    public void setComment(@NotBlank String comment) {
        this.comment = comment;
    }

    public @NotBlank String getAuthor() {
        return author;
    }

    public void setAuthor(@NotBlank String author) {
        this.author = author;
    }

    public @NotBlank String getExecutor() {
        return executor;
    }

    public void setExecutor(@NotBlank String executor) {
        this.executor = executor;
    }
}
