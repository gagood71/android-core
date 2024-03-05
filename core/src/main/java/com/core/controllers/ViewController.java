package com.core.controllers;

public abstract class ViewController<T> extends Controller<T> {
    protected static final String REQUEST_CODE = " request code：";
    protected static final String RESULT_CODE = " result code：";
    protected static final String PERMISSIONS_REQUEST = " request permissions by：";
    protected static final String PERMISSION_REJECT = " reject permission by：";
    protected static final String PERMISSION_AGREE = " agree permission by：";

    protected String tag = getClass().getName();

    public abstract void onCreate();

    public abstract void onAfterCreate();
}
