package com.example.demo.service.integradora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.integradora.service.LoanService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationsController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/book/{bookId}")
    public ResponseEntity<?> getReservationsByBook(@PathVariable Integer bookId) {
        Integer[] list = loanService.getReservationList(bookId);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteReservation(@RequestParam Integer userId, @RequestParam Integer bookId) {
        boolean removed = loanService.cancelReservation(userId, bookId);
        if (removed) return ResponseEntity.ok("Reserva eliminada.");
        else return ResponseEntity.ok("No se encontró reserva para esos datos.");
    }

}
