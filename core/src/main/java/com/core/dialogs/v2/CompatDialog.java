package com.core.dialogs.v2;

import android.content.DialogInterface;
import android.os.Bundle;

import com.core.activities.v1.CompatActivity;
import com.core.controllers.DialogController;
import com.core.dialogs.DefaultDialog;

@SuppressWarnings("rawtypes")
public abstract class CompatDialog<T extends DialogController> extends DefaultDialog
        implements DialogInterface.OnDismissListener {
    protected T controller;

    public CompatDialog(CompatActivity<?> compatActivity) {
        super(compatActivity);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(getViewId());
            setOnDismissListener(this);

            activity.attach(this);

            onAfterCreate();

            if (getViewController() != null) {
                controller = getViewController().newInstance();
                controller.setContext(this);
                controller.onCreate();
                controller.onAfterCreate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        dismiss();
    }

    protected abstract Class<T> getViewController();
}
