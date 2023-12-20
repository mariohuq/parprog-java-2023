package ru.spbstu.telematics.java;

import java.util.Arrays;

public class MyMultiKey<K> {
    private final K[] keys;
    private final int hashCode;

    public MyMultiKey(K... keys) {
        this.keys = keys;
        this.hashCode = Arrays.hashCode(keys);
    }

    public K[] getKeys() {
        return keys.clone();
    }
    public K getKey(int index) {
        return keys[index];
    }

    public int size() {
        return this.keys.length;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof MyMultiKey) {
            MyMultiKey<?> otherMulti = (MyMultiKey)other;
            return Arrays.equals(this.keys, otherMulti.keys);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
