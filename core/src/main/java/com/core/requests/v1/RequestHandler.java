package com.core.requests.v1;

public abstract class RequestHandler<T> {
    public abstract void onStart();

    public abstract void onSuccess(T data);

    public abstract void onFailure(String error);
}
