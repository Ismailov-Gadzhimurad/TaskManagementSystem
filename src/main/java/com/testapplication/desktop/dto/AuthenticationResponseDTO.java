package com.testapplication.desktop.dto;

import lombok.Getter;

@Getter
public class AuthenticationResponseDTO {

    private final String accessToken;

    private final String refreshToken;


    public AuthenticationResponseDTO(String token, String refreshToken) {
        this.accessToken = token;
        this.refreshToken = refreshToken;
    }

}
