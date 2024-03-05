package com.core.requests;

import com.core.requests.v1.RequestHandler;

public class CompatRequestHandler<T extends Response> extends RequestHandler<T> {
    @Override
    public void onStart() {
    }

    @Override
    public void onSuccess(T data) {
    }

    @Override
    public void onFailure(String error) {
    }
}
