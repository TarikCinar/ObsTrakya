package com.example.obs.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HaberlerYardimci extends SQLiteOpenHelper {

    public HaberlerYardimci(@Nullable Context context) {
        super(context, "haberler", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE haberler(haber_id INTEGER PRIMARY KEY AUTOINCREMENT,resim TEXT,baslik TEXT,altBaslik TEXT,link TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS haberler ");

    }
}
