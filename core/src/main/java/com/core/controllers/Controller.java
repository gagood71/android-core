package com.core.controllers;

public abstract class Controller<T> {
    protected T context;

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

    public T getContext() {
        return context;
    }
}
