package com.testapplication.desktop.services;


import com.testapplication.desktop.config.MyUserDetails;
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
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<MyUser> myUserOptional = userRepository.findByUsername(username);
            MyUser myUser = myUserOptional.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));


            if (myUser.getUsername() == null || myUser.getPassword() == null) {
                throw new UsernameNotFoundException("Incomplete user data for: " + username);
            }
            return new MyUserDetails(myUser);

        } catch (Exception ex) {
            throw new UsernameNotFoundException("Error loading user: " + username, ex);
        }
    }
}
