package com.example.demo.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Book {
    private Long id;
    private String bookname;
    private String isbn;
    private BigDecimal price;
    private String newold;
    private String talk;
    private Long userid;
    private String Category;
    private String Img;
    private String author;
}
