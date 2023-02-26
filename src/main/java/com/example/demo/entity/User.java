package com.example.demo.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String password;
    private int age;
    private String email;
    private String phone;
}
