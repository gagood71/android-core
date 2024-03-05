package com.core.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.core.activities.v1.CompatActivity;

@SuppressWarnings("rawtypes")
public abstract class DefaultDialog extends Dialog
        implements DialogInterface.OnDismissListener {
    protected CompatActivity activity;

    protected Window window;
    protected WindowManager.LayoutParams params;

    public DefaultDialog(CompatActivity compatActivity) {
        super(compatActivity);

        activity = compatActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        window = getWindow();
        params = window.getAttributes();

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    protected abstract void onAfterCreate();

    protected abstract int getViewId();

    public CompatActivity getCompatActivity() {
        return activity;
    }
}
