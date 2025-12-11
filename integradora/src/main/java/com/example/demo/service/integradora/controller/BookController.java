package com.example.demo.service.integradora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.integradora.dto.ErrorResponse;
import com.example.demo.service.integradora.model.Book;
import com.example.demo.service.integradora.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // 1. POST: Registrar
    @PostMapping
    public String register(@RequestBody Book book) {
        Book created = bookService.registerBook(book);
        return "Libro registrado con ID: " + created.id;
    }

    // 2. GET: Consultar Catálogo
    @GetMapping
    public Book[] getCatalog() {
        return bookService.getAllActiveBooks();
    }

    // 3. PUT: Actualizar
    @PutMapping("/{id}")
    public String update(@PathVariable int id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    // 4. DELETE: Baja Lógica
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        return bookService.deleteBookLogically(id);
    }

    // 5. GET: Obtener libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Book book = bookService.findBookById(id);
        if (book == null) {
            ErrorResponse err = new ErrorResponse();
            err.setError("Libro no encontrado");
            err.setDetail("ID: " + id);
            return ResponseEntity.status(404).body(err);
        }
        return ResponseEntity.ok(book);
    }
}
