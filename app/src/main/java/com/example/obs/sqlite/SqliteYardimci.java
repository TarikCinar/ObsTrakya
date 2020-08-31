package com.example.obs.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteYardimci extends SQLiteOpenHelper {

    public SqliteYardimci(@Nullable Context context) {
        super(context, "duyurular", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE duyurular(duyuru_id INTEGER PRIMARY KEY AUTOINCREMENT,duyuru_ad TEXT,duyuru_link TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS duyurular ");

    }
}
