package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ForwardLinked<T> implements Iterable<T> {
    private int size;
    private int modCount;
    private Node<T> head;
    private Node<T> tail;

    public void add(T value) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
        modCount++;
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        Node<T> resultNode = head;
        for (int i = 0; i < index; i++) {
            resultNode = resultNode.next;
        }
        return resultNode.item;
    }

    public T deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        T result = head.item;
        Node<T> oldHead = head;
        head = head.next;
        oldHead.next = null;
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Node<T> cursor = head;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return cursor != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T result = cursor.item;
                cursor = cursor.next;
                return result;
            }
        };
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;

        Node(T element, Node<T> next) {
            this.item = element;
            this.next = next;
        }
    }
}