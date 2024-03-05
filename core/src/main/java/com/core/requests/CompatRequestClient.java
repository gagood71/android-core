package com.core.requests;

import com.core.configuration.Configuration;
import com.core.requests.v1.RequestClient;
import com.core.requests.v1.RequestHandler;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;

public class CompatRequestClient<T extends Response> extends RequestClient<T> {
    @Override
    public void post(String url) {
        new Thread(() -> {
            try {
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod(POST);
                connection.setUseCaches(false);
                connection.setDoOutput(true);
                connection.setConnectTimeout((int) Configuration.Time.TIME_1000);

                post();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    protected void onSuccess(String responseString,
                             RequestHandler<T> handler) {
        try {
            data = new Gson().fromJson(responseString, className);
        } catch (Exception e) {
            e.printStackTrace();

            onFailure(e.getMessage(), handler);
        }
    }

    @Override
    protected void onFailure(String responseString,
                             RequestHandler<T> handler) {
    }
}
