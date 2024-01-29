package ru.clevertec.cache;

public interface LRUCache<K, V> {

    V get(K key);

    void put(K key, V value);

    void remove(K key);
}
