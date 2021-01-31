package com.github.antoinejt.atodo.utils;

import java.util.HashMap;
import java.util.Map;

public class BiMap<T, U> {
    private final Map<T, U> normal;
    private final Map<U, T> inverted;

    public BiMap() {
        normal = new HashMap<>();
        inverted = new HashMap<>();
    }

    public void put(T t, U u) {
        normal.put(t, u);
        inverted.put(u, t);
    }

    public Map<T, U> get() {
        return normal;
    }

    public Map<U, T> inverted() {
        return inverted;
    }
}
