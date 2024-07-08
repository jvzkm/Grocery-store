package com.store.util.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@UtilityClass
public class JsonUtil {
    private static ObjectMapper mapper;

    public static void setMapper(@Autowired ObjectMapper mapper) {
        JsonUtil.mapper = mapper;
    }

    public static <T> List<T> readValues(String json, Class<T> clazz) {
        ObjectReader reader = mapper.readerFor(clazz);
        try {
            return reader.<T>readValues(json).readAll();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read array from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> T readValue(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> String writeValue(T obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    public static <T> String writeAdditionProps(T obj, String addName, Object addValue) {
        Map<String, Object> map = mapper.convertValue(obj, new TypeReference<>() {
        });
        map.put(addName, addValue);
        return writeValue(map);
    }

    public static <T> Matcher<T> usingAssertions(Class<T> clazz) {
        return new Matcher<>(clazz);
    }

    public static <T> Matcher<T> usingEqualsComparator(Class<T> clazz) {
        return usingAssertions(clazz);
    }

    @RequiredArgsConstructor
    public static class Matcher<T> {
        private final Class<T> clazz;

        public boolean contentJson(T expected, String actualJson) {
            return assertMatch(expected, JsonUtil.readValue(actualJson, clazz));
        }

        public boolean contentJson(Iterable<T> expected, String actualJson) {
            return assertMatch(expected, JsonUtil.readValues(actualJson, clazz));
        }

        private boolean assertMatch(T expected, T actual) {
            assertEquals(expected, actual);
            return true;
        }

        private boolean assertMatch(Iterable<T> expected, Iterable<T> actual) {
            assertIterableEquals(expected, actual);
            return true;
        }


        public T readFromJson(String actualJson) throws UnsupportedEncodingException {
            return JsonUtil.readValue(actualJson, clazz);
        }


    }


}