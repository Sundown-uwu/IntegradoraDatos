package com.example.demo.service.integradora.structures;

public class MySinglyLinkedList<T> {
    private Node<T> head;
    private int size;

    public MySinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public Node<T> getHead() {
        return head;
    }
    
    public int getSize() {
        return size;
    }
}
