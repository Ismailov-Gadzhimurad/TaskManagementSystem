package com.testapplication.desktop.dto;


import com.testapplication.desktop.models.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class UserDTO {
    @Setter
    @Getter
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 255, message = "Username must be between 3 and 255 characters")
    private String username;

    @Setter
    @Getter
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Roles are required")
    private Role role; // Consider changing to List<String> roles for multiple roles

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}