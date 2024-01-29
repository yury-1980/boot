package ru.clevertec.cache.impl;

import lombok.Data;

@Data
public class Node<K, V> {

    private K key;
    private V value;
    private Node<K, V> prev;
    private Node<K, V> next;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
