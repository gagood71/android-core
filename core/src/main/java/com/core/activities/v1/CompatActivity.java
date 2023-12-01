package com.core.activities.v1;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.activities.DefaultActivity;
import com.core.controllers.ActivityController;

import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public abstract class CompatActivity<T extends ActivityController>
        extends DefaultActivity {
    protected T controller;

    protected ArrayList<Dialog> dialogs;

    private boolean isCreated = false;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialogs = new ArrayList<>();

        try {
            setContentView(getViewId());
            onAfterCreate();

            if (getViewController() != null) {
                controller = getViewController().newInstance();
                controller.setContext(this);
                controller.onCreate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!isCreated) {
            if (controller != null) {
                controller.onAfterCreate();
            }
        }

        isCreated = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (controller != null) {
            controller.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        for (Dialog dialog : dialogs) {
            dialog.dismiss();
        }

        if (controller != null) {
            controller.onDestroy();
        }
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (controller != null) {
            controller.onActivityResult(
                    requestCode,
                    resultCode,
                    data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (controller != null) {
            controller.onRequestPermissionsResult(
                    requestCode,
                    permissions,
                    grantResults);
        }
    }

    public void attach(Dialog dialog) {
        dialogs.add(dialog);
    }

    protected abstract void onAfterCreate();

    protected abstract Class<T> getViewController();

    protected abstract int getViewId();
}
