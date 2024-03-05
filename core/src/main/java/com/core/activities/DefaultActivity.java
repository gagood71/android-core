package com.core.activities;

import static android.os.Process.killProcess;
import static android.os.Process.myPid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public abstract class DefaultActivity extends AppCompatActivity {
    private static final long SPECIAL_CODE = 120;

    private boolean isStartActivated = false;

    protected ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (isStartActivated) {
            isStartActivated = false;
        }
    }

    private Toast makeText(String text) {
        return Toast.makeText(this, text, Toast.LENGTH_SHORT);
    }

    private Toast makeText(int resourceId) {
        return Toast.makeText(this, resourceId, Toast.LENGTH_SHORT);
    }

    public void showToast(int resourceId) {
        makeText(resourceId).show();
    }

    public void showCenterToast(String text) {
        Toast toast = makeText(text);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void showCenterToast(int resourceId) {
        Toast toast = makeText(resourceId);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void startActivity(Class<? extends AppCompatActivity> activityClass,
                              Bundle extras) {
        if (isStartActivated) {
            return;
        }

        isStartActivated = true;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(extras);
        intent.setClass(this, activityClass);

        startActivity(intent);
    }

    public void startActivity(Uri uri) {
        if (isStartActivated) {
            return;
        }

        isStartActivated = true;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);

        startActivity(intent);
    }

    public void startActivity(String text) {
        if (isStartActivated) {
            return;
        }

        isStartActivated = true;

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(intent, null);
        startActivity(shareIntent);
    }

    public void registerPermission(String permission, int code) {
        registerPermissions(new String[]{permission}, code);
    }

    public void registerPermissions(String[] permissions, int code) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    public void exit() {
        try {
            killProcess(myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isNetworkConnected() {
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            return activeNetworkInfo != null &&
                    activeNetworkInfo.isConnected();
        }

        return false;
    }

    public boolean isWifiNetwork() {
        return connectivityManager.getActiveNetworkInfo().getType()
                == ConnectivityManager.TYPE_WIFI;
    }

    public boolean isMobileNetwork() {
        return connectivityManager.getActiveNetworkInfo().getType()
                == ConnectivityManager.TYPE_MOBILE;
    }

    public boolean isPermissionGranted(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isPermissionsGranted(String[] permissions) {
        boolean isGranted = true;

        for (String permission : permissions) {
            if (!isPermissionGranted(permission)) {
                isGranted = false;
                break;
            }
        }

        return isGranted;
    }

    public int dp2px(long value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    public ActivityResultLauncher<Intent> getResultLauncher(ActivityResultCallback callback) {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK ||
                            result.getResultCode() == SPECIAL_CODE) {
                        callback.granted();
                    } else {
                        callback.denied();
                    }
                }
        );
    }
}
