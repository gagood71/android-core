package com.core.requests;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

public abstract class Request {
    public StringBuilder getParams() {
        StringBuilder stringBuilder = new StringBuilder();
        Field[] fields = getClass().getFields();

        int i = 0;
        for (Field field : fields) {
            try {
                stringBuilder.append(field.getName());
                stringBuilder.append("=");
                stringBuilder.append(field.get(this));

                if ((i + 1) < fields.length) {
                    i++;

                    stringBuilder.append("&");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder;
    }

    @NonNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        Field[] fields = getClass().getFields();

        int i = 0;
        for (Field field : fields) {
            try {
                stringBuilder.append(field.getName());
                stringBuilder.append("：").append(field.get(this));

                if ((i + 1) < fields.length) {
                    i++;

                    stringBuilder.append(",");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }
}
