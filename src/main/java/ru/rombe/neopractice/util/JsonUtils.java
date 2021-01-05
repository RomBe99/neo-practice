package ru.rombe.neopractice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.Map;

public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toJson(Object o) throws Exception {
        return MAPPER.writeValueAsString(o);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <C extends Collection<?>> C collectionFromJson(String json) {
        TypeReference<C> collectionType = new TypeReference<>() {
        };

        try {
            return MAPPER.readValue(json, collectionType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

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