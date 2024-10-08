package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class NonCollisionMap<K, V> implements SimpleMap<K, V> {
    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;
    private int size = 0;
    private int modCount = 0;
    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        expand();
        int index = indexFor(hash(hashcode(key)));
        boolean result = table[index] == null;
        if (result) {
            table[index] = new MapEntry<>(key, value);
            size++;
            modCount++;
        }
        return result;
    }

    @Override
    public V get(K key) {
        V result = null;
        int index = indexFor(hash(hashcode(key)));
        if (table[index] != null
                && hashcode(key) == hashcode(table[index].key)
                && Objects.equals(key, table[index].key)) {
            result = table[index].value;
        }
        return result;
    }

    @Override
    public boolean remove(K key) {
        int index = indexFor(hash(hashcode(key)));
        boolean result = table[index] != null
                && hashcode(key) == hashcode(table[index].key)
                && Objects.equals(key, table[index].key);
        if (result) {
            table[index] = null;
            modCount++;
            size--;
        }
        return result;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {

            private final int expectedModCount = modCount;
            private int count = 0;
            private int index = 0;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                while (count < size && table[index] == null) {
                    index++;
                }
                return count < size;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                count++;
                return  table[index++].key;
            }
        };
    }

    private int hashcode(K key) {
        return Objects.hashCode(key);
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (table.length - 1);
    }

    private void expand() {
        if (size / LOAD_FACTOR >= capacity) {
            MapEntry<K, V>[] tempTable = table;
            capacity *= 2;
            size = 0;
            table = new MapEntry[capacity];
            for (MapEntry<K, V> mapEntry : tempTable) {
                if (mapEntry != null) {
                    put(mapEntry.key, mapEntry.value);
                }
            }
        }
    }

    private static class MapEntry<K, V> {
        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}