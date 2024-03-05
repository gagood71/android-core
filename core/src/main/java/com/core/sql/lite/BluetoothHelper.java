package com.core.sql.lite;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.core.CompatApp;
import com.core.sql.lite.models.Bluetooth;

import java.util.ArrayList;
import java.util.List;

public class BluetoothHelper extends SQLiteHelper<Bluetooth> {
    protected static final String NAME_FIELD = "name";
    protected static final String ADDRESS_FIELD = "address";

    public BluetoothHelper(@Nullable Context context,
                           @Nullable String name,
                           @Nullable SQLiteDatabase.CursorFactory factory,
                           int version) {
        super(context, name, factory, version);
    }

    @Override
    protected void create(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    protected void upgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    @Override
    protected String getCreateSQL() {
        return "CREATE TABLE IF NOT EXISTS " + getTableName() +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_FIELD + " VARCHAR(255)," +
                ADDRESS_FIELD + " VARCHAR(255))";
    }

    @Override
    protected ContentValues getCreateValues() {
        ContentValues values = new ContentValues();
        values.put("id", "1");
        values.put(NAME_FIELD, "NULL");
        values.put(ADDRESS_FIELD, "");

        return values;
    }

    @Override
    protected String getTableName() {
        return "bluetooth";
    }

    @Override
    public String[] getTableFields() {
        return new String[]{
                "id",
                "name",
                "address"
        };
    }

    @Override
    public void drop() {
        // 刪除所有資料
        SQLiteDatabase database = getWritableDatabase();
        database.delete(getTableName(), null, null);
    }

    @Override
    public List<Bluetooth> getData() {
        dataList = new ArrayList<>();

        if (getReadableDatabase() != null) {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();

            // 查詢資料
            Cursor cursor = sqLiteDatabase.query(
                    getTableName(), // 資料表名稱
                    getTableFields(), // 要查詢的欄位
                    null, // 查詢條件 (null 代表不設定條件)
                    null, // 條件參數
                    null, // GROUP BY 子句
                    null, // HAVING 子句
                    null // 排序方式
            );


            if (cursor != null) {
                // 將查詢結果轉換為資料物件
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_FIELD));
                    String address = cursor.getString(cursor.getColumnIndexOrThrow(ADDRESS_FIELD));

                    dataList.add(new Bluetooth(name, address));
                }

                cursor.close();
            }
        }

        return dataList;
    }

    public void upgrade(BluetoothDevice device) {
        if (getWritableDatabase() != null) {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues values = new ContentValues();

            if (ActivityCompat.checkSelfPermission(CompatApp.getContext(), Manifest.permission.BLUETOOTH_CONNECT)
                    == PackageManager.PERMISSION_GRANTED) {
                values.put(NAME_FIELD, device.getName());
            }

            values.put(ADDRESS_FIELD, device.getAddress());

            sqLiteDatabase.update(getTableName(), values, "id = ?", new String[]{"1"});
        }
    }

    public boolean isEmpty() {
        if (getReadableDatabase() != null) {
            try {
                SQLiteDatabase sqLiteDatabase = getReadableDatabase();
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + getTableName() +
                        " WHERE id = ?", new String[]{"1"});

                if (cursor == null) {
                    return true;
                }

                cursor.moveToFirst();

                int columIndex;
                if (cursor.getColumnIndex(ADDRESS_FIELD) > 0) {
                    columIndex = cursor.getColumnIndex(ADDRESS_FIELD);

                    return cursor.getString(columIndex).isEmpty();
                }
            } catch (Exception e) {
                e.printStackTrace();

                SQLiteManager.getLog().insert(e.getMessage());

                return true;
            }
        }

        return true;
    }

    public boolean isSameBluetoothDevice(BluetoothDevice device) {
        if (getReadableDatabase() != null) {
            try {
                SQLiteDatabase sqLiteDatabase = getReadableDatabase();
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + getTableName() +
                        " WHERE id = ?", new String[]{"1"});

                if (cursor == null) {
                    return false;
                }

                cursor.moveToFirst();

                int columIndex;
                if (cursor.getColumnIndex(ADDRESS_FIELD) > 0) {
                    columIndex = cursor.getColumnIndex(ADDRESS_FIELD);

                    return cursor.getString(columIndex).equals(device.getAddress());
                }
            } catch (Exception e) {
                e.printStackTrace();

                SQLiteManager.getLog().insert(e.getMessage());

                return false;
            }
        }

        return false;
    }

    public String getDeviceName() {
        String name = "NULL";

        if (getReadableDatabase() != null) {
            try {
                SQLiteDatabase sqLiteDatabase = getReadableDatabase();
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + getTableName() +
                        " WHERE id = ?", new String[]{"1"});

                if (cursor == null) {
                    return name;
                }

                cursor.moveToFirst();

                int columIndex;
                if (cursor.getColumnIndex(NAME_FIELD) > 0) {
                    columIndex = cursor.getColumnIndex(NAME_FIELD);

                    name = cursor.getString(columIndex);
                }
            } catch (Exception e) {
                e.printStackTrace();

                SQLiteManager.getLog().insert(e.getMessage());
            }
        }

        return name;
    }

    public String getDeviceAddress() {
        String name = "NULL";

        if (getReadableDatabase() != null) {
            try {
                SQLiteDatabase sqLiteDatabase = getReadableDatabase();
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + getTableName() +
                        " WHERE id = ?", new String[]{"1"});

                if (cursor == null) {
                    return name;
                }

                cursor.moveToFirst();

                int columIndex;
                if (cursor.getColumnIndex(ADDRESS_FIELD) > 0) {
                    columIndex = cursor.getColumnIndex(ADDRESS_FIELD);

                    name = cursor.getString(columIndex);
                }
            } catch (Exception e) {
                e.printStackTrace();

                SQLiteManager.getLog().insert(e.getMessage());
            }
        }

        return name;
    }
}
