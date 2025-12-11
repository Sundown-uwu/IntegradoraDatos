package com.example.demo.service.integradora.structures;

public class CustomStack<T> {
    private Object[] data;
    private int top;

    public CustomStack(int capacity) {
        this.data = new Object[capacity];
        this.top = 0;
    }

    public boolean push(T value) {
        if (top == data.length) return false; // full
        data[top++] = value;
        return true;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (top == 0) return null;
        T value = (T) data[--top];
        data[top] = null;
        return value;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (top == 0) return null;
        return (T) data[top - 1];
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public int size() {
        return top;
    }

    @SuppressWarnings("unchecked")
    public Object[] toArray() {
        Object[] arr = new Object[top];
        for (int i = 0; i < top; i++) arr[i] = data[i];
        return arr;
    }
}
