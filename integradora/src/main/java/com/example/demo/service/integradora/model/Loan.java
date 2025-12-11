package com.example.demo.service.integradora.model;

public class Loan {
    public Integer id;
    public Integer userId;
    public Integer bookId;
    public Boolean active;

    public Loan() {}

    public Loan(Integer id, Integer userId, Integer bookId) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.active = true;
    }
}
