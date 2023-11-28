package com.core.sql.lite;

import com.core.CompatApp;
import com.core.sql.lite.BluetoothHelper;
import com.core.sql.lite.LogHelper;

public class SQLiteManager {
    private static LogHelper LOG_HELPER;
    private static BluetoothHelper BLUETOOTH_HELPER;

    public static LogHelper getLog() {
        if (LOG_HELPER == null) {
            LOG_HELPER = new LogHelper(
                    CompatApp.getContext(),
                    "log",
                    null,
                    1);
        }

        return LOG_HELPER;
    }

    public static BluetoothHelper getBluetooth() {
        if (BLUETOOTH_HELPER == null) {
            BLUETOOTH_HELPER = new BluetoothHelper(
                    CompatApp.getContext(),
                    "bluetooth",
                    null,
                    1);
        }

        return BLUETOOTH_HELPER;
    }
}
