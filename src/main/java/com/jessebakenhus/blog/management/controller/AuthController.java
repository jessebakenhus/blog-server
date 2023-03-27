package com.jessebakenhus.blog.management.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class AuthController {

    @Autowired
    FakeUserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {

            UserDetails userDetails = userRepository.loadUserByUsername(username);

            if (password.equalsIgnoreCase(userDetails.getPassword())) {
                String token = Jwts.builder()
                        .setSubject(userDetails.getUsername())
                        .signWith(SignatureAlgorithm.HS256, "b2c7053769bc44b49baa8c55398bd040".getBytes())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                        .compact();

                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.badRequest().body("Invalid username or password");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }
}
