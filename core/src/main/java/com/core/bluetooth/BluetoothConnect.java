package com.core.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import androidx.annotation.RequiresPermission;

public class BluetoothConnect {
    public static final String OBD = "OBD";

    @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
    public static void connect(String address,
                               String type,
                               BluetoothListener listener) {
        if (type.equals(OBD)) {
            new Thread(new BluetoothRunnable(address, listener) {
                @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
                @Override
                protected BluetoothSocket getBluetoothSocket() {
                    BluetoothDevice device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);

                    try {
                        return device.createInsecureRfcommSocketToServiceRecord(uuid);
                    } catch (Exception e) {
                        e.printStackTrace();

                        return null;
                    }
                }
            }).start();
        }
    }
}
