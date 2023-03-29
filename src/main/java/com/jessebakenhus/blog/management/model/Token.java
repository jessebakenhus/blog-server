package com.jessebakenhus.blog.management.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class Token {
    String accessToken;
}
