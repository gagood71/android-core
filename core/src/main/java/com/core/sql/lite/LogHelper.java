package com.core.sql.lite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.core.CompatApp;
import com.core.R;
import com.core.sql.lite.models.Log;
import com.core.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class LogHelper extends SQLiteHelper<Log> {
    protected static final String MESSAGE_FIELD = "message";
    protected static final String TIME_FIELD = "time";

    public LogHelper(@Nullable Context context,
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
                MESSAGE_FIELD + " VARCHAR(255)," +
                TIME_FIELD + " VARCHAR(255))";
    }

    @Override
    protected ContentValues getCreateValues() {
        ContentValues values = new ContentValues();
        values.put("id", "1");
        values.put(MESSAGE_FIELD, CompatApp.getContext().getString(R.string.系統初始化));
        values.put(TIME_FIELD, DateUtil.getCurrentDate());

        return values;
    }

    @Override
    protected String getTableName() {
        return "system_log";
    }

    @Override
    protected String[] getTableFields() {
        return new String[]{"id", "message", "time"};
    }

    @Override
    public void drop() {
        // 刪除所有資料
        SQLiteDatabase database = getWritableDatabase();
        database.delete(getTableName(), "id != ?", new String[]{"1"});
    }

    @Override
    public List<Log> getData() {
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
                    String message = cursor.getString(cursor.getColumnIndexOrThrow(MESSAGE_FIELD));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(TIME_FIELD));

                    dataList.add(new Log(message, time));
                }

                cursor.close();
            }
        }

        return dataList;
    }

    public void insert(String message) {
        if (getWritableDatabase() != null) {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(MESSAGE_FIELD, message);
            values.put(TIME_FIELD, DateUtil.getCurrentDate());

            sqLiteDatabase.insert(getTableName(), null, values);
        }
    }
}
