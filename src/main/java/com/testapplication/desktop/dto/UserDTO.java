package com.testapplication.desktop.dto;


import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Roles are required")
    private String roles;

    public @NotBlank(message = "Username is required") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username is required") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Password is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Roles are required") String getRoles() {
        return roles;
    }

    public void setRoles(@NotBlank(message = "Roles are required") String roles) {
        this.roles = roles;
    }
}
