package com.core.services.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import com.core.activities.ActivityResultCallback;
import com.core.activities.v1.CompatActivity;
import com.core.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

// https://developer.android.com/about/versions/12/features/bluetooth-permissions?hl=zh-cn
// https://www.cnblogs.com/fly263/p/16715525.html
public class BluetoothService {
    public static void enable(CompatActivity<?> activity,
                              ActivityResultCallback callback) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

        if (adapter != null) {
            if (adapter.isEnabled()) {
                callback.granted();
            } else {
                activity.getResultLauncher(callback)
                        .launch(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
            }
        } else {
            callback.denied();
        }
    }

    public static void register(CompatActivity<?> activity,
                                ActivityResultCallback callback) {
        String[] permissions = getPermissions();

        if (!activity.isPermissionsGranted(permissions)) {
            activity.registerPermissions(
                    permissions,
                    Configuration.Permission.BLUETOOTH_PERMISSIONS
            );
        } else {
            callback.granted();
        }
    }

    public static List<BluetoothDevice> getBondedDevices(CompatActivity<?> activity) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

        List<BluetoothDevice> devices = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT)
                    == PackageManager.PERMISSION_GRANTED) {
                devices = new ArrayList<>(adapter.getBondedDevices());
            }
        } else {
            devices = new ArrayList<>(adapter.getBondedDevices());
        }

        return devices;
    }

    public static String[] getPermissions() {
        List<String> permissions = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(Manifest.permission.BLUETOOTH_ADVERTISE);
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT);
            permissions.add(Manifest.permission.BLUETOOTH_SCAN);
        } else {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissions.add(Manifest.permission.BLUETOOTH);
            permissions.add(Manifest.permission.BLUETOOTH_ADMIN);
        }

        String[] permissionArray = new String[permissions.size()];
        permissions.toArray(permissionArray);

        return permissionArray;
    }
}
