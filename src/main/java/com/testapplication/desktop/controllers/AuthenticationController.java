package com.testapplication.desktop.controllers;


import com.testapplication.desktop.dto.AuthenticationResponseDTO;
import com.testapplication.desktop.dto.LoginRequestDTO;
import com.testapplication.desktop.dto.RegistrationRequestDTO;
import com.testapplication.desktop.services.AuthenticationService;
import com.testapplication.desktop.services.MyUserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final MyUserServiceImpl myUserService;

    public AuthenticationController(AuthenticationService authenticationService, MyUserServiceImpl myUserService) {
        this.authenticationService = authenticationService;
        this.myUserService = myUserService;
    }



    @PostMapping("/registration")
    public ResponseEntity<String> register(
            @RequestBody RegistrationRequestDTO registrationDto) {

        if(myUserService.existsByUsername(registrationDto.getUsername())) {
            return ResponseEntity.badRequest().body("Имя пользователя уже занято");
        }

        authenticationService.register(registrationDto);

        return ResponseEntity.ok("Регистрация прошла успешно");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
            @RequestBody LoginRequestDTO request) {

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<AuthenticationResponseDTO> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        return authenticationService.refreshToken(request, response);
    }

}