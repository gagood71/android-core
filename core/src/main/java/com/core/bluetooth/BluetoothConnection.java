package com.core.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.core.activities.v1.CompatActivity;
import com.core.configuration.Configuration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class BluetoothConnection {
    private static final UUID BLUETOOTH_MODULE_UUID = UUID.randomUUID();

    private static final String METHOD = "createInsecureRfcommSocketToServiceRecord";

    private static BluetoothSocket SOCKET;

    private static Timer TIMER;
    private static TimerTask TIMERTASK;

    public static void connection(CompatActivity<?> activity,
                                  BluetoothDevice device,
                                  BluetoothListener listener) {
        listener.isConnecting();

        try {
            Method method = device.getClass().getMethod(METHOD, UUID.class);

            SOCKET = (BluetoothSocket) method.invoke(device, BLUETOOTH_MODULE_UUID);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();

            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT)
                            == PackageManager.PERMISSION_GRANTED) {
                        SOCKET = device.createRfcommSocketToServiceRecord(BLUETOOTH_MODULE_UUID);
                    }
                } else {
                    SOCKET = device.createRfcommSocketToServiceRecord(BLUETOOTH_MODULE_UUID);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT)
                        == PackageManager.PERMISSION_GRANTED) {
                    SOCKET.connect();
                }
            } else {
                if (SOCKET != null) {
                    SOCKET.connect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        TIMER = new Timer();
        TIMERTASK = new TimerTask() {
            @Override
            public void run() {
                if (SOCKET.isConnected()) {
                    listener.isConnected(SOCKET);
                } else {
                    try {
                        SOCKET.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    listener.isNotConnected();
                }
            }
        };
        TIMER.schedule(
                TIMERTASK,
                Configuration.Time.TIME_0,
                Configuration.Time.TIME_500
        );
    }

    public static void disconnect() {
        TIMERTASK.cancel();
        TIMER.cancel();
    }
}
