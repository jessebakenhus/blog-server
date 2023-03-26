package com.jessebakenhus.blog.management.model;

import lombok.Value;

import java.util.Date;

@Value
public class Blog {

    String slug;
    boolean draft;
    String content;
}
