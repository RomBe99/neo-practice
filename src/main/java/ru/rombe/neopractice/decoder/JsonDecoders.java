package ru.rombe.neopractice.decoder;

import ru.rombe.neopractice.util.JsonUtils;

import java.util.*;

/**
 * This class contains methods for decoding data from json string.
 */
public class JsonDecoders {
    /**
     * Convert json string to map with contained sets.
     *
     * @param json json string for encoding
     * @param <K>  type of keys
     * @param <V>  type of values contained in sets
     * @return map contained sets in values
     */
    public static <K, V> Map<K, Set<V>> mapToSetFromJson(String json) {
        Map<K, Collection<V>> temp = JsonUtils.mapFromJson(json);
        Map<K, Set<V>> result = new LinkedHashMap<>();

        for (K k : temp.keySet()) {
            result.put(k, new LinkedHashSet<>(temp.get(k)));
        }

        return result;
    }
}