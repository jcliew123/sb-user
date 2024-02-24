package com.example.jcliew.UserManagement.service;

import com.example.jcliew.UserManagement.entity.User;
import com.example.jcliew.UserManagement.model.SignUpModel;
import com.example.jcliew.UserManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public UserDetails signUp(SignUpModel signUpModel) {
        if(userRepository.findByUsername(signUpModel.username()) != null) {
            throw new RuntimeException("Username already exists");
        }
        String encodedPassword = new BCryptPasswordEncoder().encode(signUpModel.password());
        User newUser = new User(signUpModel.username(), encodedPassword, signUpModel.role());
        return userRepository.save(newUser);
    }
}
