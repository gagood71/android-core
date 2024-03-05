package com.core.services.bluetooth;

import android.bluetooth.BluetoothDevice;

public interface BluetoothReceiverListener {
    void onActionFound(BluetoothDevice device);
}
