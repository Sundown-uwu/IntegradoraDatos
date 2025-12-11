package com.example.demo.service.integradora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.integradora.model.HistoryAction;
import com.example.demo.service.integradora.service.LoanService;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private LoanService loanService;

    @GetMapping
    public ResponseEntity<?> getHistory() {
        HistoryAction[] history = loanService.getHistory();
        return ResponseEntity.ok(history);
    }

    @PostMapping("/undo")
    public ResponseEntity<?> undo() {
        String result = loanService.undoLastAction();
        return ResponseEntity.ok(result);
    }
}
