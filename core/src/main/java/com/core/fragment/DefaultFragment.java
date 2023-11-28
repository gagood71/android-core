package com.core.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.core.activities.v1.CompatActivity;

@SuppressWarnings("rawtypes")
public abstract class DefaultFragment extends Fragment {
    protected CompatActivity activity;

    public DefaultFragment(CompatActivity compatActivity) {
        activity = compatActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = View.inflate(activity, getViewId(), null);

        onAfterCreate(view);

        return view;
    }

    public CompatActivity getCompatActivity() {
        return activity;
    }

    protected abstract void onAfterCreate(View view);

    public abstract String getTitle();

    protected abstract int getViewId();
}
