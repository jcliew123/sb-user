package com.example.jcliew.UserManagement.controller;

import com.example.jcliew.UserManagement.config.auth.TokenProvider;
import com.example.jcliew.UserManagement.entity.User;
import com.example.jcliew.UserManagement.model.JwtModel;
import com.example.jcliew.UserManagement.model.SignInModel;
import com.example.jcliew.UserManagement.model.SignUpModel;
import com.example.jcliew.UserManagement.service.AuthService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    private final AuthService authService;

//    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    private static final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpModel model) {
        authService.signUp(model);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtModel> signIn(@RequestBody SignInModel model) throws JsonProcessingException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(model.username(), model.password());
        Authentication authUser = authenticationManager.authenticate(authenticationToken);
        System.out.println("authUser: " + mapper.writeValueAsString(authUser));
        String token = tokenProvider.generateAccessToken((User) authUser.getPrincipal());
        return ResponseEntity.ok(new JwtModel(token));
    }
}
