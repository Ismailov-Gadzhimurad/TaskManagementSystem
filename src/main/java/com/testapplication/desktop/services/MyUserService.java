package com.testapplication.desktop.services;


import com.testapplication.desktop.models.MyUser;
import com.testapplication.desktop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public interface MyUserService extends UserDetailsService {

    boolean existsByUsername(String username);







}
