package com.testapplication.desktop.dto;

import lombok.Data;

@Data
public class RegistrationRequestDTO {

    private String username;
    private String email;
    private String password;
}
