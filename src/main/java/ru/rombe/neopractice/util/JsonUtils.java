package ru.rombe.neopractice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.Map;

/**
 * This class is a facade for the json mapper allowing you to perform various operations with json strings.
 */
public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Convert object to json string
     *
     * @param o object that will be converted to json string
     * @return json string representing the received object
     */
    public static String toJson(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert json string to collection.
     *
     * @param json json contained collection
     * @param <C>  type of collection (it needs to identify the type stored in the collection)
     * @return collection
     */
    public static <C extends Collection<?>> C collectionFromJson(String json) {
        TypeReference<C> collectionType = new TypeReference<>() {
        };

        try {
            return MAPPER.readValue(json, collectionType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert json string to map.
     *
     * @param jsonWithMap json string with map
     * @param <K>         type of keys stored in map
     * @param <V>         type of values stored in map
     * @return map from json
     */
    public static <K, V> Map<K, V> mapFromJson(String jsonWithMap) {
        TypeReference<Map<K, V>> mapType = new TypeReference<>() {
        };

        try {
            return MAPPER.readValue(jsonWithMap, mapType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}