package com.jessebakenhus.blog.management.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser {

    @JsonProperty("username")
    String username;

    @JsonProperty("password")
    String password;
}
