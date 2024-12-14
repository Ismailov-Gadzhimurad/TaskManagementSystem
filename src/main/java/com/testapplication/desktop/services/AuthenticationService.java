package com.testapplication.desktop.services;

import com.testapplication.desktop.dto.AuthenticationResponseDTO;
import com.testapplication.desktop.dto.LoginRequestDTO;
import com.testapplication.desktop.dto.RegistrationRequestDTO;
import com.testapplication.desktop.models.MyUser;
import com.testapplication.desktop.models.Role;
import com.testapplication.desktop.models.Token;
import com.testapplication.desktop.repo.TokenRepository;
import com.testapplication.desktop.repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;


    public AuthenticationService(UserRepository userRepository,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    public void register(RegistrationRequestDTO request) {

        MyUser myUser = new MyUser();

        myUser.setUsername(request.getUsername());
        myUser.setPassword(passwordEncoder.encode(request.getPassword()));
        myUser.setRole(Role.USER);

        userRepository.save(myUser);
    }

    private void revokeAllToken(MyUser myUser) {

        List<Token> validTokens = tokenRepository.findAllAccessTokenByUser(myUser.getId());

        if(!validTokens.isEmpty()){
            validTokens.forEach(t ->{
                t.setLoggedOut(true);
            });
        }

        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String accessToken, String refreshToken, MyUser myUser) {

        Token token = new Token();

        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(myUser);

        tokenRepository.save(token);
    }


    public AuthenticationResponseDTO authenticate(LoginRequestDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        MyUser myUser = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        String accessToken = jwtService.generateAccessToken(myUser);
        String refreshToken = jwtService.generateRefreshToken(myUser);

        revokeAllToken(myUser);

        saveUserToken(accessToken, refreshToken, myUser);

        return new AuthenticationResponseDTO(accessToken, refreshToken);
    }

    public ResponseEntity<AuthenticationResponseDTO> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);

        MyUser myUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found"));

        if (jwtService.isValidRefresh(token, myUser)) {

            String accessToken = jwtService.generateAccessToken(myUser);
            String refreshToken = jwtService.generateRefreshToken(myUser);

            revokeAllToken(myUser);

            saveUserToken(accessToken, refreshToken, myUser);

            return new ResponseEntity<>(new AuthenticationResponseDTO(accessToken, refreshToken), HttpStatus.OK);

        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}