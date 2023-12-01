package com.core.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;

import com.core.activities.ActivityResultCallback;
import com.core.activities.v1.CompatActivity;
import com.core.configuration.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// https://developer.android.com/about/versions/12/features/bluetooth-permissions?hl=zh-cn
// https://www.cnblogs.com/fly263/p/16715525.html
public class BluetoothService {
    private static final UUID BLUETOOTH_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static BluetoothSocket BLUETOOTH_SOCKET;

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
        // 先檢查藍芽是否開啟，再檢查權限
        enable(activity, new ActivityResultCallback() {
            @Override
            public void granted() {
                String[] permissions = getPermissions();

                activity.getResultLauncher(callback);

                if (!activity.isPermissionsGranted(permissions)) {
                    activity.registerPermissions(
                            permissions,
                            Configuration.Permission.BLUETOOTH_PERMISSIONS
                    );
                } else {
                    callback.granted();
                }
            }

            @Override
            public void denied() {
                callback.denied();
            }
        });
    }

    @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
    public static void connect(BluetoothDevice device,
                               BluetoothListener listener) {
        new Thread(() -> {
            listener.isConnecting();

            try {
                BLUETOOTH_SOCKET = device.createRfcommSocketToServiceRecord(BLUETOOTH_MODULE_UUID);
            } catch (IOException e) {
                disconnect();

                listener.isNotConnected();
            }

            try {
                BLUETOOTH_SOCKET.connect(); // 這裡進行連接

                // 如果連接成功，通知 listener
                if (BLUETOOTH_SOCKET.isConnected()) {
                    listener.isConnected(BLUETOOTH_SOCKET);
                } else {
                    disconnect();

                    listener.isNotConnected();
                }
            } catch (IOException e) {
                disconnect();

                listener.isNotConnected();
            }
        }).start();
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

    private static void disconnect() {
        if (BLUETOOTH_SOCKET != null) {
            try {
                BLUETOOTH_SOCKET.close();
                BLUETOOTH_SOCKET = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
