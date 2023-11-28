package com.core.requests.v1;

import android.os.Handler;
import android.os.Looper;

import com.core.requests.Request;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public abstract class RequestClient<T> {
    protected static final String POST = "POST";
    protected static final String GET = "GET";

    protected Class<T> className;
    protected Request request;
    protected RequestHandler<T> handler;

    protected T data;

    protected HttpURLConnection connection;

    public RequestClient() {
    }

    private String getResponseString() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String string;

        while ((string = reader.readLine()) != null) {
            stringBuilder.append(string);
        }

        reader.close();

        return stringBuilder.toString();
    }

    protected void post() {
        new Handler(Looper.getMainLooper()).post(() -> handler.onStart());

        try {
            String params = request.getParams().toString();

            // 寫入參數到請求主體
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.write(params.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    try {
                        onSuccess(getResponseString(), handler);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                new Handler(Looper.getMainLooper()).post(() -> {
                    try {
                        onFailure(getResponseString(), handler);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RequestClient<T> convert(Class<T> c) {
        className = c;

        return this;
    }

    public RequestClient<T> request(Request r) {
        request = r;

        return this;
    }

    public RequestClient<T> handler(RequestHandler<T> h) {
        handler = h;

        return this;
    }

    public abstract void post(String url);

    protected abstract void onSuccess(String responseString, RequestHandler<T> handler);

    protected abstract void onFailure(String responseString, RequestHandler<T> handler);
}
