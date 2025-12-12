package com.example.demo.service.integradora.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.demo.service.integradora.model.Book;
import com.example.demo.service.integradora.structures.MySinglyLinkedList;
import com.example.demo.service.integradora.structures.Node;

@Service
public class BookService {

    private MySinglyLinkedList<Book> catalog = new MySinglyLinkedList<>();
    private int idCounter = 1;

    public Book registerBook(Book book) {
        book.id = idCounter++;
        book.active = true;
        if (book.availableCopies == null) book.availableCopies = 1;
        if (book.waitlist == null) book.waitlist = new com.example.demo.service.integradora.structures.CustomQueue<>(10);
        catalog.add(book);
        return book;
    }

    public Book findBookById(int id) {
        Node<Book> current = catalog.getHead();
        while (current != null) {
            if (current.data != null && Objects.equals(current.data.id, id)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public Book[] getAllActiveBooks() {
        int activeCount = 0;
        Node<Book> current = catalog.getHead();
        while (current != null) {
            if (Boolean.TRUE.equals(current.data.active)) {
                activeCount++;
            }
            current = current.next;
        }

        Book[] resultArray = new Book[activeCount];
        
        //Llenar el array
        int index = 0;
        current = catalog.getHead(); // Reiniciar puntero
        while (current != null) {
            if (Boolean.TRUE.equals(current.data.active)) {
                resultArray[index++] = current.data;
            }
            current = current.next;
        }
        
        return resultArray;
    }

    // actualizar libro
    public String updateBook(int id, Book bookDetails) {
        Node<Book> current = catalog.getHead();
        
        while (current != null) {
            if (Objects.equals(current.data.id, id) && Boolean.TRUE.equals(current.data.active)) {
                current.data.title = bookDetails.title;
                current.data.author = bookDetails.author;
                return "Libro actualizado: " + current.data.title;
            }
            current = current.next;
        }
        return "Error: Libro no encontrado o inactivo.";
    }

    public String deleteBookLogically(int id) {
        Node<Book> current = catalog.getHead();
        
        while (current != null) {
            if (Objects.equals(current.data.id, id)) {
                if (!Boolean.TRUE.equals(current.data.active)) return "El libro ya estaba dado de baja.";
                
                current.data.active = false;
                return "Libro dado de baja correctamente (ID: " + id + ")";
            }
            current = current.next;
        }
        return "Error: Libro no encontrado.";
    }

}