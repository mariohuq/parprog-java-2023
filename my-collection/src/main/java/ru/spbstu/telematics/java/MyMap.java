package ru.spbstu.telematics.java;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MyMap<Key, Value> {
    @Nullable Value get(@NotNull Key key);

    default boolean containsKey(@NotNull Key key) {
        return get(key) != null;
    }

    void put(@NotNull Key key, @NotNull Value value);

    @Nullable Value remove(@NotNull Key key);

    int size();

    boolean isEmpty();
}
