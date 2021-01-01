package ru.rombe.neopractice.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonUtils {
    private static final Gson MAPPER = new Gson();

    public static String toJson(Object o) {
        return MAPPER.toJson(o);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return MAPPER.fromJson(json, clazz);
    }

    public static <K, V> Map<K, V> mapFromJson(String jsonWithMap) {
        Type mapType = new TypeToken<Map<K, V>>() {
        }.getType();

        return MAPPER.fromJson(jsonWithMap, mapType);
    }
}