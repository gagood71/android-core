package com.core.services.bluetooth;

import android.bluetooth.BluetoothSocket;

public interface BluetoothListener {
    void isConnecting();

    void isConnected(BluetoothSocket socket);

    void isNotConnected();
}
