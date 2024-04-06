package ru.clevertec.cache.impl;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.cache.LRUCache;

import java.util.HashMap;
import java.util.Map;

@ToString
@Component
public class LRUCacheImpl<K, V> implements LRUCache<K, V> {

    private final int capacity;
    private Node<K, V> head;
    private Node<K, V> tail;
    private final Map<K, Node<K, V>> map;

    public LRUCacheImpl(@Value("${Cache.capacity}") int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
    }

    @Override
    public V get(K key) {
        if (map.get(key) == null) {
            return null;
        }

        Node<K, V> item = map.get(key);
        // Перемещаем в конец
        removeNode(item);
        addToTail(item);

        return item.getValue();
    }

    @Override
    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node<K, V> item = map.get(key);
            item.setValue(value);

            // Перемещаем в конец
            removeNode(item);
            addToTail(item);
        } else {
            if (map.size() >= capacity) {
                // Удаляем голову
                map.remove(head.getKey());
                removeNode(head);
            }

            // Добавляем в конец
            Node<K, V> node = new Node<>(key, value);
            addToTail(node);
            map.put(key, node);
        }
    }

    public void remove(K key) {
        if (map.containsKey(key)) {
            Node<K, V> item = map.get(key);
            removeNode(item);
            map.remove(key);
        }
    }

    private void removeNode(Node<K, V> node) {
        if (node.getPrev() != null) {
            node.getPrev()
                    .setNext(node.getNext());
        } else {
            head = node.getNext();
        }

        if (node.getNext() != null) {
            node.getNext()
                    .setPrev(node.getPrev());
        } else {
            tail = node.getPrev();
        }
    }

    private void addToTail(Node<K, V> node) {
        if (tail != null) {
            tail.setNext(node);
        }

        node.setPrev(tail);
        node.setNext(null);
        tail = node;

        if (head == null) {
            head = tail;
        }
    }
}