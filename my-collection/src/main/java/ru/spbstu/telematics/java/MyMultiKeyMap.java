package ru.spbstu.telematics.java;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MyMultiKeyMap<K, V>
        implements MyMap<MyMultiKey<? extends K>,V>, Iterable<MyMap.Entry<MyMultiKey<? extends K>,V>> {

    final MyHashMap<MyMultiKey<? extends K>,V> delegate;

    public MyMultiKeyMap() {
        this.delegate = new MyHashMap<>();
    }

    /**
     * @return null если нет значения по ключу
     */
    public V get(final K key1, final K key2) {
        if (delegate.table == null) {
            return null;
        }
        final int hashCode = hash(key1, key2);
        var entry = delegate.table[delegate.index(hashCode)];
        while (entry != null) {
            if (entry.hash == hashCode && isEqualKey(entry, key1, key2)) {
                return entry.getValue();
            }
            entry = entry.next;
        }
        return null;
    }

    public boolean containsKey(final K key1, final K key2) {
        final int hashCode = hash(key1, key2);
        var entry = delegate.table[delegate.index(hashCode)];
        while (entry != null) {
            if (entry.hash == hashCode && isEqualKey(entry, key1, key2)) {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    public void put(final K key1, final K key2, final V value) {
        put(new MyMultiKey<>(key1, key2), value);
    }

    /**
     * @return значение по удаленному ключу, null если такого ключа не было
     */
    public V removeMultiKey(final Object key1, final Object key2) {
        final int hashCode = hash(key1, key2);
        final int index = delegate.index(hashCode);
        MyHashMap<MyMultiKey<? extends K>, V>.Node entry = delegate.table[index];
        MyHashMap<MyMultiKey<? extends K>, V>.Node previous = null;
        while (entry != null) {
            if (entry.hash == hashCode && isEqualKey(entry, key1, key2)) {
                final V oldValue = entry.getValue();
                if (previous == null) {
                    delegate.table[index] = null;
                } else {
                    previous.next = null;
                }
                delegate.size--;
                return oldValue;
            }
            previous = entry;
            entry = entry.next;
        }
        return null;
    }

    /**
     *
     * То же, что `Objects.hash(key1, key2)`, но без дополнительного создания объекта
     */
    private int hash(final Object key1, final Object key2) {
        int result = 1;
        result = 31 * result + Objects.hashCode(key1);
        result = 31 * result + Objects.hashCode(key2);
        return result;
    }

    private boolean isEqualKey(final Entry<MyMultiKey<? extends K>, V> entry,
            final Object key1, final Object key2) {
        final MyMultiKey<? extends K> multi = entry.getKey();
        return multi.size() == 2 &&
            Objects.equals(key1, multi.getKey(0)) &&
            Objects.equals(key2, multi.getKey(1));
    }

    @Override
    public @Nullable V get(@NotNull MyMultiKey<? extends K> myMultiKey) {
        return delegate.get(myMultiKey);
    }

    @Override
    public void put(@NotNull MyMultiKey<? extends K> myMultiKey, @NotNull V v) {
        delegate.put(myMultiKey, v);
    }

    @Override
    public @Nullable V remove(@NotNull MyMultiKey<? extends K> myMultiKey) {
        return delegate.remove(myMultiKey);
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @NotNull
    @Override
    public Iterator<Entry<MyMultiKey<? extends K>, V>> iterator() {
        return delegate.iterator();
    }
}
