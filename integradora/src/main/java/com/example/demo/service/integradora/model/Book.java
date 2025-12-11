package com.example.demo.service.integradora.model;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Book {
    public Integer id;
    public String title;
    public String author;
    public Boolean active;
    public Integer availableCopies;
    @JsonIgnore
    public com.example.demo.service.integradora.structures.CustomQueue<Integer> waitlist;


    // Constructor vacío necesario para recibir JSON
    public Book() {}

    public Book(Integer id, String title, String author, Integer availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.active = true; // Por defecto nace activo
        this.availableCopies = availableCopies;
        
        this.waitlist = new com.example.demo.service.integradora.structures.CustomQueue<>(10);


    }
}
