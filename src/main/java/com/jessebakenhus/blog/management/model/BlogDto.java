package com.jessebakenhus.blog.management.model;

import lombok.Value;

@Value
public class BlogDto {

    String slug;
    boolean draft;
}
