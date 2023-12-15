package com.core.sql.lite;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class SQLiteHelper<T> extends SQLiteOpenHelper {
    protected List<T> dataList;

    public SQLiteHelper(@Nullable Context context,
                        @Nullable String name,
                        @Nullable SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }

    public SQLiteHelper(@Nullable Context context,
                        @Nullable String name,
                        @Nullable SQLiteDatabase.CursorFactory factory,
                        int version,
                        @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        dataList = new ArrayList<>();

        sqLiteDatabase.execSQL(getCreateSQL());
        sqLiteDatabase.insert(getTableName(), null, getCreateValues());

        create(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        upgrade(sqLiteDatabase, i, i1);
    }

    protected abstract void create(SQLiteDatabase sqLiteDatabase);

    protected abstract void upgrade(SQLiteDatabase sqLiteDatabase, int i, int i1);

    protected abstract String getCreateSQL();

    protected abstract ContentValues getCreateValues();

    protected abstract String getTableName();

    protected abstract String[] getTableFields();

    public abstract void drop();

    public abstract List<T> getData();
}
