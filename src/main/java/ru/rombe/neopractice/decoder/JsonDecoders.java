package ru.rombe.neopractice.decoder;

import ru.rombe.neopractice.util.JsonUtils;

import java.util.*;

public class JsonDecoders {
    public static <K, V> Map<K, Set<V>> mapFromJson(String json) {
        Map<K, Collection<V>> temp = JsonUtils.mapFromJson(json);
        Map<K, Set<V>> result = new LinkedHashMap<>();

        for (K k : temp.keySet()) {
            result.put(k, new LinkedHashSet<>(temp.get(k)));
        }

        return result;
    }
}