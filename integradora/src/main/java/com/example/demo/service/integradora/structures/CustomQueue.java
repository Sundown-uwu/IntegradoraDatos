package com.example.demo.service.integradora.structures;

public class CustomQueue<T> {
    private Object[] data;
    private int front;
    private int rear;
    private int size;

    public CustomQueue(int capacity) {
        this.data = new Object[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public boolean offer(T value) {
        if (size == data.length) return false; // full
        data[rear] = value;
        rear = (rear + 1) % data.length;
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    public T poll() {
        if (size == 0) return null;
        T value = (T) data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        return value;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (size == 0) return null;
        return (T) data[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    public int positionOf(T value) {
        for (int i = 0; i < size; i++) {
            int idx = (front + i) % data.length;
            T curr = (T) data[idx];
            if (curr == null && value == null) return i + 1;
            if (curr != null && curr.equals(value)) return i + 1;
        }
        return -1;
    }

    public boolean remove(T value) {
        int pos = positionOf(value);
        if (pos == -1) return false;
        int len = data.length;
        int removeIdx = (front + (pos - 1)) % len;
        for (int i = 0; i < size - (pos - 1) - 1; i++) {
            int from = (removeIdx + 1 + i) % len;
            int to = (removeIdx + i) % len;
            data[to] = data[from];
        }
        rear = (rear - 1 + len) % len;
        data[rear] = null;
        size--;
        return true;
    }

    @SuppressWarnings("unchecked")
    public Object[] toArray() {
        Object[] arr = new Object[size];
        for (int i = 0; i < size; i++) {
            int idx = (front + i) % data.length;
            arr[i] = data[idx];
        }
        return arr;
    }
}
