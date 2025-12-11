package com.example.demo.service.integradora.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.demo.service.integradora.model.User;
import com.example.demo.service.integradora.structures.MySinglyLinkedList;
import com.example.demo.service.integradora.structures.Node;

@Service
public class UserService {

    private MySinglyLinkedList<User> users = new MySinglyLinkedList<>();
    private int idCounter = 1;

    public User createUser(User user) {
        if (user.name != null) user.name = user.name.trim();
        if (user.name == null || user.name.isEmpty()) return null;
        if (existsByName(user.name)) return null;
        user.id = idCounter++;
        user.active = true;
        users.add(user);
        return user;
    }

    public boolean existsByName(String name) {
        if (name == null) return false;
        Node<User> current = users.getHead();
        while (current != null) {
            if (current.data != null && name.equalsIgnoreCase(current.data.name)) return true;
            current = current.next;
        }
        return false;
    }

    public User[] getAllUsers() {
        int count = 0;
        Node<User> current = users.getHead();
        while (current != null) {
            count++;
            current = current.next;
        }
        User[] result = new User[count];
        int i = 0;
        current = users.getHead();
        while (current != null) {
            result[i++] = current.data;
            current = current.next;
        }
        return result;
    }

    public User findUserById(Integer id) {
        Node<User> current = users.getHead();
        while (current != null) {
            if (Objects.equals(current.data.id, id)) return current.data;
            current = current.next;
        }
        return null;
    }
}
