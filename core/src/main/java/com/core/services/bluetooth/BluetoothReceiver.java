package com.core.services.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.core.activities.v1.CompatActivity;

public class BluetoothReceiver {
    private static final BroadcastReceiver RECEIVER = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action != null) {
                Log.e(getClass().getName(), action);
            }

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT)
                        == PackageManager.PERMISSION_GRANTED) {
                    if (device != null) {
                        String message = "{DEVICE_STATE：BOND_NONE" +
                                ",DEVICE_NAME：" + device.getName() +
                                ",DEVICE_ADDRESS：" + device.getAddress() + "}";

                        Log.e(getClass().getName(), message);

                        if (device.getName() != null) {
                            LISTENER.onActionFound(device);
                        }
                    }
                }
            }
        }
    };

    private static BluetoothReceiverListener LISTENER;

    public static void start(CompatActivity<?> activity,
                             BluetoothReceiverListener listener) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN)
                == PackageManager.PERMISSION_GRANTED) {
            LISTENER = listener;

            activity.registerReceiver(RECEIVER, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
            activity.registerReceiver(RECEIVER, new IntentFilter(BluetoothDevice.ACTION_FOUND));

            BluetoothAdapter.getDefaultAdapter().startDiscovery();
        }
    }

    public static void cancel(CompatActivity<?> activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN)
                == PackageManager.PERMISSION_GRANTED) {
            if (BluetoothAdapter.getDefaultAdapter().isDiscovering()) {
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
            }
        }
    }
}
