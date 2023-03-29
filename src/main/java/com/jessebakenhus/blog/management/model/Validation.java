package com.jessebakenhus.blog.management.model;

import lombok.Value;

@Value
public class Validation {

    String token;
    boolean valid;
}
