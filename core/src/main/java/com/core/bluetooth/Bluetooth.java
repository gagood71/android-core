package com.core.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;

import com.core.activities.ActivityResultCallback;
import com.core.activities.v1.CompatActivity;
import com.core.configuration.Configuration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class Bluetooth {
    private static final UUID BLUETOOTH_MODULE_UUID = UUID.randomUUID();

    private static final String METHOD = "createInsecureRfcommSocketToServiceRecord";

    CompatActivity<?> activity;

    BluetoothReceiver receiver;

    BluetoothSocket socket;

    ActivityResultLauncher<Intent> launcher;

    Timer timer;
    TimerTask timerTask;

    public Bluetooth(CompatActivity<?> compatActivity) {
        activity = compatActivity;
        receiver = new BluetoothReceiver(compatActivity);
    }

    public void disable() {
        receiver.cancel();

        receiver = null;
        timer = null;
        timerTask = null;

        Log.e(getClass().getName(), "DISABLE RECEIVER");
    }

    public void register(ActivityResultCallback callback) {
        String[] permissions = getPermissions();

        if (launcher == null) {
            launcher = activity.getResultLauncher(callback);
        }

        if (!activity.isPermissionsGranted(permissions)) {
            activity.registerPermissions(
                    permissions,
                    Configuration.Permission.BLUETOOTH_PERMISSIONS
            );
        } else {
            callback.granted();
        }
    }

    /**
     * @param listener
     * @link https://developer.android.com/about/versions/12/features/bluetooth-permissions?hl=zh-cn
     * @link https://www.cnblogs.com/fly263/p/16715525.html
     */
    public void request(BluetoothReceiverListener listener) {
        String[] permissions = getPermissions();

        if (launcher == null) {
            launcher = activity.getResultLauncher(
                    new ActivityResultCallback() {
                        @Override
                        public void granted() {
                            receiver.cancel();
                            receiver.start(listener);
                        }

                        @Override
                        public void denied() {
                        }
                    });
        }

        if (!activity.isPermissionsGranted(permissions)) {
            activity.registerPermissions(
                    permissions,
                    Configuration.Permission.BLUETOOTH_PERMISSIONS
            );
        } else {
            launcher.launch(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));

            Log.e(getClass().getName(), "ENABLE BLUETOOTH ADAPTER");
        }
    }

    public void connection(BluetoothDevice device, BluetoothListener listener) {
        listener.isConnecting();

        try {
            Method method = device.getClass().getMethod(METHOD, UUID.class);

            socket = (BluetoothSocket) method.invoke(device, BLUETOOTH_MODULE_UUID);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();

            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT)
                            == PackageManager.PERMISSION_GRANTED) {
                        socket = device.createRfcommSocketToServiceRecord(BLUETOOTH_MODULE_UUID);
                    }
                } else {
                    socket = device.createRfcommSocketToServiceRecord(BLUETOOTH_MODULE_UUID);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT)
                        == PackageManager.PERMISSION_GRANTED) {
                    socket.connect();
                }
            } else {
                if (socket != null) {
                    socket.connect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (socket.isConnected()) {
                    listener.isConnected(socket);
                } else {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    listener.isNotConnected();
                }
            }
        };
        timer.schedule(
                timerTask,
                Configuration.Time.TIME_0,
                Configuration.Time.TIME_500
        );
    }

    public void disconnect() {
        timerTask.cancel();
        timer.cancel();

        Log.e(getClass().getName(), "DISCONNECT BLUETOOTH");
    }

    public String[] getPermissions() {
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
