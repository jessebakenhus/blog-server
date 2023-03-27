package com.jessebakenhus.blog.management.repository;

import com.jessebakenhus.blog.management.model.FakeUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Repository
public class FakeUserRepository implements UserDetailsService {

    private final List<FakeUser> users = List.of(
            new FakeUser("admin", System.getenv().get("ADMIN_PASSWORD"),
                    Arrays.asList(
                            new SimpleGrantedAuthority("ROLE_USER"),
                            new SimpleGrantedAuthority("ROLE_ADMIN")))
    );

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

