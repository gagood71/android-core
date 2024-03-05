package com.core.services.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;

import androidx.annotation.RequiresPermission;

import com.core.configuration.Configuration;
import com.core.sql.lite.SQLiteManager;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

// https://developer.android.com/reference/android/bluetooth/BluetoothDevice.html#createInsecureRfcommSocketToServiceRecord(java.util.UUID)
public abstract class BluetoothRunnable implements Runnable {
    protected UUID uuid;

    protected BluetoothSocket bluetoothSocket;

    protected BluetoothListener listener;

    protected String address;

    public BluetoothRunnable(String deviceAddress,
                             BluetoothListener bluetoothListener) {
        listener = bluetoothListener;
        address = deviceAddress;
    }

    @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
    @Override
    public void run() {
        BluetoothDevice device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);
        ParcelUuid[] parcelUuids = device.getUuids();

        uuid = parcelUuids[0].getUuid();

        connect();
    }

    @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
    protected void connect() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (bluetoothSocket.isConnected()) {
                    // 如果連接成功，通知 listener
                    listener.isConnected(bluetoothSocket);
                } else {
                    disconnect();

                    listener.isNotConnected();
                }
            }
        };

        try {
            bluetoothSocket = getBluetoothSocket();

            if (bluetoothSocket != null) {
                bluetoothSocket.connect(); // 這裡進行連接

                timer.schedule(timerTask, Configuration.Time.TIME_10000);
            }
        } catch (Exception e) {
            disconnect();

            SQLiteManager.getLog().insert(e.getMessage());

            timerTask.cancel();
            timer.cancel();

            listener.isNotConnected();
        }
    }

    public void disconnect() {
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket.close();

                bluetoothSocket = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract BluetoothSocket getBluetoothSocket();
}
