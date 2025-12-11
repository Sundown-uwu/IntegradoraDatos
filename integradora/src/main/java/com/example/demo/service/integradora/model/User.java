package com.example.demo.service.integradora.model;

public class User {
    public Integer id;
    public String name;
    public Boolean active;

    public User() {}

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.active = true;
    }
}
