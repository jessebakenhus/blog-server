package com.jessebakenhus.blog.management.controller;

import com.jessebakenhus.blog.management.model.AuthUser;
import com.jessebakenhus.blog.management.model.Token;
import com.jessebakenhus.blog.management.model.Validation;
import com.jessebakenhus.blog.management.repository.FakeUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin
@RestController
public class AuthController {

    @Autowired
    FakeUserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody AuthUser authUser) {
        try {

            UserDetails userDetails = userRepository.loadUserByUsername(authUser.getUsername());

            if (authUser.getPassword().equalsIgnoreCase(userDetails.getPassword())) {
                String token = Jwts.builder()
                        .setSubject(userDetails.getUsername())
                        .signWith(SignatureAlgorithm.HS256, System.getenv().get("JWT_TOKEN").getBytes())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                        .compact();

                return ResponseEntity.ok(Token.of(token));
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (UsernameNotFoundException e) {
            return  ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("validate/{token}")
    public ResponseEntity<Validation> valid(@PathVariable String token) {
        return ResponseEntity.ok(new Validation(token, false));
    }
}
