package com.example.demo.service.integradora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.integradora.dto.ErrorResponse;
import com.example.demo.service.integradora.model.User;
import com.example.demo.service.integradora.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (user == null || user.name == null) {
            ErrorResponse err = new ErrorResponse();
            err.setError("Datos incompletos");
            err.setDetail("name es requerido");
            return ResponseEntity.badRequest().body(err);
        }
        // Esta cosita verifica que no se repitan nombres :D
        if (userService.existsByName(user.name)) {
            ErrorResponse err = new ErrorResponse();
            err.setError("Nombre duplicado");
            err.setDetail("Ya existe un usuario con ese nombre");
            return ResponseEntity.badRequest().body(err);
        }

        User created = userService.createUser(user);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public User[] listUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        User u = userService.findUserById(id);
        if (u == null) {
            ErrorResponse err = new ErrorResponse();
            err.setError("Usuario no encontrado");
            err.setDetail("ID: " + id);
            return ResponseEntity.status(404).body(err);
        }
        return ResponseEntity.ok(u);
    }
}
