package com.core.controllers;

import com.core.dialogs.DefaultDialog;

public abstract class DialogController<T extends DefaultDialog>
        extends ViewController<T> {
    public DialogController() {
        super();
    }
}
