package com.core.bluetooth;

import android.bluetooth.BluetoothDevice;

public interface BluetoothReceiverListener {
    void onActionFound(BluetoothDevice device);
}
