package com.core.controllers;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.core.fragment.DefaultFragment;

import java.util.Arrays;

public abstract class FragmentController<T extends DefaultFragment> extends ViewController<T> {
    public FragmentController() {
        super();
    }

    /**
     * Fragment > onActivityResult
     */
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        Log.e(tag, REQUEST_CODE + requestCode);
        Log.e(tag, RESULT_CODE + resultCode);
    }

    /**
     * Fragment > onRequestPermissionsResult
     */
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.e(tag, REQUEST_CODE + requestCode);
        Log.e(tag, PERMISSIONS_REQUEST + Arrays.toString(permissions));
    }

    /**
     * Fragment > onStart（多次觸發）
     */
    public abstract void onStart();

    /**
     * Fragment > onPause
     */
    public abstract void onPause();

    /**
     * Fragment > onDestroy
     */
    public abstract void onDestroy();
}
