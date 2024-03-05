package com.core.dialogs.v1;

import android.content.DialogInterface;
import android.os.Bundle;

import com.core.activities.v1.CompatActivity;
import com.core.dialogs.DefaultDialog;

public abstract class CompatDialog extends DefaultDialog
        implements DialogInterface.OnDismissListener {
    public CompatDialog(CompatActivity<?> compatActivity) {
        super(compatActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getViewId());
        setOnDismissListener(this);

        activity.attach(this);

        onAfterCreate();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        dismiss();
    }
}
