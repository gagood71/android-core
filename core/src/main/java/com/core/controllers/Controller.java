package com.core.controllers;

import com.core.requests.Response;

import java.util.HashMap;
import java.util.Map;

public abstract class Controller<T> {
    protected T context;

    protected Map<String, Response> dynamicResponses = new HashMap<>();

    public void setContext(T object) {
        context = object;
    }

    public boolean isEmpty(String value) {
        return value == null ||
                value.equals("") ||
                value.equals("null");
    }

    public boolean isEmpty(Object value) {
        return value == null;
    }

    // 動態新增參數
    public void putResponse(String key, Response value) {
        dynamicResponses.put(key, value);
    }

    public T getContext() {
        return context;
    }

    // 動態獲取參數
    public Response getResponse(String key) {
        return dynamicResponses.get(key);
    }
}
