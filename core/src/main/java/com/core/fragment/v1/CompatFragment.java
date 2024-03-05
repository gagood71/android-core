package com.core.fragment.v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.activities.v1.CompatActivity;
import com.core.controllers.FragmentController;
import com.core.fragment.DefaultFragment;

@SuppressWarnings("rawtypes")
public abstract class CompatFragment<T extends FragmentController>
        extends DefaultFragment {
    protected T controller;

    public CompatFragment(CompatActivity compatActivity,
                          Class<T> object) {
        super(compatActivity);

        try {
            controller = object.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = View.inflate(activity, getViewId(), null);

        onAfterCreate(view);

        if (controller != null) {
            controller.setContext(this);
            controller.onCreate();
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (controller != null) {
            controller.onAfterCreate();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (controller != null) {
            controller.onDestroy();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 @Nullable Intent data) {
        super.onActivityResult(
                requestCode,
                resultCode,
                data);

        if (controller != null) {
            controller.onActivityResult(
                    requestCode,
                    resultCode,
                    data);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults);

        if (controller != null) {
            controller.onRequestPermissionsResult(
                    requestCode,
                    permissions,
                    grantResults);
        }
    }
}
