package com.example.demo.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {
    private Long id;
    private String text;
    private Timestamp time;
    private Long bookuserid;
    private Long fromuserid;
    private Long bookid;
    private String fromusername;
}
