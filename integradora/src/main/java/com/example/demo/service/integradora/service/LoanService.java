package com.example.demo.service.integradora.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.service.integradora.model.Book;
import com.example.demo.service.integradora.model.HistoryAction;
import com.example.demo.service.integradora.model.Loan;
import com.example.demo.service.integradora.structures.CustomStack;
import com.example.demo.service.integradora.structures.MySinglyLinkedList;

@Service
public class LoanService {

    @Autowired
    private BookService bookService;

    private MySinglyLinkedList<Loan> loans = new MySinglyLinkedList<>();
    private int loanIdCounter = 1;

    // Historial de acciones (pila)
    private CustomStack<HistoryAction> history = new CustomStack<>(200);

    public String requestLoan(Integer userId, Integer bookId) {
        if (userId == null || bookId == null) return "userId and bookId are required";

        Book book = bookService.findBookById(bookId);
        if (book == null || !Boolean.TRUE.equals(book.active)) return "Libro no encontrado o inactivo";

        if (book.availableCopies != null && book.availableCopies > 0) {
            Loan loan = new Loan(loanIdCounter++, userId, bookId);
            loans.add(loan);
            book.availableCopies = book.availableCopies - 1;
            history.push(new HistoryAction(HistoryAction.ActionType.CREATE_LOAN, userId, bookId, loan.id));
            return "Préstamo creado. ID préstamo: " + loan.id;
        } else {
            // agregar a la cola de espera
            if (book.waitlist == null) book.waitlist = new com.example.demo.service.integradora.structures.CustomQueue<>(10);
            boolean offered = book.waitlist.offer(userId);
            history.push(new HistoryAction(HistoryAction.ActionType.ADD_TO_WAITLIST, userId, bookId, null));
            if (offered) return "No hay copias. Usuario agregado a lista de espera.";
            else return "Lista de espera llena.";
        }
    }

    public String returnLoan(Integer loanId) {
        if (loanId == null) return "loanId is required";

        // Buscar prestamo
        com.example.demo.service.integradora.structures.Node<Loan> current = loans.getHead();
        Loan found = null;
        while (current != null) {
            if (Objects.equals(current.data.id, loanId) && Boolean.TRUE.equals(current.data.active)) {
                found = current.data;
                break;
            }
            current = current.next;
        }

        if (found == null) return "Préstamo no encontrado o ya devuelto.";

        found.active = false;
        history.push(new HistoryAction(HistoryAction.ActionType.RETURN, found.userId, found.bookId, found.id));

        Book book = bookService.findBookById(found.bookId);
        if (book == null) return "Libro asociado no encontrado.";

        if (book.waitlist != null && !book.waitlist.isEmpty()) {
            Integer nextUser = book.waitlist.poll();
            Loan autoLoan = new Loan(loanIdCounter++, nextUser, book.id);
            loans.add(autoLoan);
            history.push(new HistoryAction(HistoryAction.ActionType.AUTO_CREATE_LOAN, nextUser, book.id, autoLoan.id));
            return "Préstamo devuelto. Usuario " + nextUser + " recibe préstamo automático (ID: " + autoLoan.id + ").";
        } else {
            if (book.availableCopies == null) book.availableCopies = 1;
            else book.availableCopies = book.availableCopies + 1;
            return "Préstamo devuelto. Copias disponibles incrementadas.";
        }
    }

    public int getReservationPosition(Integer userId, Integer bookId) {
        if (userId == null || bookId == null) return -1;
        Book book = bookService.findBookById(bookId);
        if (book == null || book.waitlist == null) return -1;
        return book.waitlist.positionOf(userId);
    }

    public boolean cancelReservation(Integer userId, Integer bookId) {
        if (userId == null || bookId == null) return false;
        Book book = bookService.findBookById(bookId);
        if (book == null || book.waitlist == null) return false;
        return book.waitlist.remove(userId);
    }

    public Integer[] getReservationList(Integer bookId) {
        if (bookId == null) return new Integer[0];
        Book book = bookService.findBookById(bookId);
        if (book == null || book.waitlist == null) return new Integer[0];
        Object[] objs = book.waitlist.toArray();
        Integer[] result = new Integer[objs.length];
        for (int i = 0; i < objs.length; i++) {
            result[i] = (Integer) objs[i];
        }
        return result;
    }

    // encontrar el prestamo por iD
    public Loan findLoanById(Integer loanId) {
        if (loanId == null) return null;
        com.example.demo.service.integradora.structures.Node<Loan> current = loans.getHead();
        while (current != null) {
            if (Objects.equals(current.data.id, loanId)) return current.data;
            current = current.next;
        }
        return null;
    }

    public Loan[] getActiveLoans() {
        int count = 0;
        com.example.demo.service.integradora.structures.Node<Loan> current = loans.getHead();
        while (current != null) {
            if (Boolean.TRUE.equals(current.data.active)) count++;
            current = current.next;
        }
        Loan[] result = new Loan[count];
        int i = 0;
        current = loans.getHead();
        while (current != null) {
            if (Boolean.TRUE.equals(current.data.active)) result[i++] = current.data;
            current = current.next;
        }
        return result;
    }

    public Loan[] getLoansByUser(Integer userId) {
        if (userId == null) return new Loan[0];
        int count = 0;
        com.example.demo.service.integradora.structures.Node<Loan> current = loans.getHead();
        while (current != null) {
            if (Objects.equals(current.data.userId, userId)) count++;
            current = current.next;
        }
        Loan[] result = new Loan[count];
        int i = 0;
        current = loans.getHead();
        while (current != null) {
            if (Objects.equals(current.data.userId, userId)) result[i++] = current.data;
            current = current.next;
        }
        return result;
    }

    public String undoLastAction() {
        HistoryAction action = history.pop();
        if (action == null) return "No hay acciones para deshacer.";

        switch (action.type) {
            case CREATE_LOAN, AUTO_CREATE_LOAN -> {
                if (action.loanId == null) return "Acción inválida sin loanId.";
                Loan loan = findLoanById(action.loanId);
                if (loan == null) return "Préstamo no encontrado para deshacer.";
                if (Boolean.TRUE.equals(loan.active)) {
                    loan.active = false;
                    Book book = bookService.findBookById(loan.bookId);
                    if (book != null) {
                        if (book.availableCopies == null) book.availableCopies = 1;
                        else book.availableCopies = book.availableCopies + 1;
                    }
                    return "Deshecho: préstamo eliminado (ID: " + loan.id + ").";
                } else {
                    return "El préstamo ya estaba inactivo.";
                }
            }
            case ADD_TO_WAITLIST -> {
                boolean removed = cancelReservation(action.userId, action.bookId);
                if (removed) return "Deshecho: usuario removido de lista de espera.";
                else return "No se encontró la reserva para remover.";
            }
            case RETURN -> {
                if (action.loanId == null) return "Acción inválida sin loanId.";
                Loan loan = findLoanById(action.loanId);
                if (loan == null) return "Préstamo no encontrado para restaurar.";
                if (!Boolean.TRUE.equals(loan.active)) {
                    loan.active = true;
                    Book book = bookService.findBookById(loan.bookId);
                    if (book != null && book.availableCopies != null && book.availableCopies > 0) {
                        book.availableCopies = book.availableCopies - 1;
                    }
                    return "Deshecho: préstamo restaurado (ID: " + loan.id + ").";
                } else {
                    return "El préstamo ya estaba activo.";
                }
            }
            default -> {
                return "Tipo de acción no soportado para undo.";
            }
        }
    }

    public HistoryAction[] getHistory() {
        Object[] objs = history.toArray();
        HistoryAction[] arr = new HistoryAction[objs.length];
        for (int i = 0; i < objs.length; i++) arr[i] = (HistoryAction) objs[i];
        return arr;
    }

}
