package com.example.demo.service.integradora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.integradora.dto.LoanRequest;
import com.example.demo.service.integradora.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody LoanRequest req) {
    
        String result = loanService.requestLoan(req.getUserId(), req.getBookId());
        return ResponseEntity.ok(result);
    }

    //POST 
    @PostMapping("/{loanId}/return")
    public ResponseEntity<?> returnLoanById(@PathVariable Integer loanId) {
        String result = loanService.returnLoan(loanId);
        return ResponseEntity.ok(result);
    }

    // GET 
    @GetMapping("/active")
    public ResponseEntity<?> getActiveLoans() {
        return ResponseEntity.ok(loanService.getActiveLoans());
    }

    // GET 
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getLoansByUser(@PathVariable Integer userId) {

        return ResponseEntity.ok(loanService.getLoansByUser(userId));
    }

    

}
