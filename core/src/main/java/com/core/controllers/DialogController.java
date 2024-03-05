package com.core.controllers;

import com.core.dialogs.v2.CompatDialog;

@SuppressWarnings({"rawtypes"})
public abstract class DialogController<T extends CompatDialog>
        extends ViewController<T> {
    public DialogController() {
        super();
    }
}
