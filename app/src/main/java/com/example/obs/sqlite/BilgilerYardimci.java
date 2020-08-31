package com.example.obs.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.SQLDataException;

public class BilgilerYardimci extends SQLiteOpenHelper {

    public BilgilerYardimci(@Nullable Context context) {
        super(context, "bilgiler", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE bilgiler(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "okulNo TEXT," +
                "isim TEXT," +
                "isimSoyisim TEXT," +
                "username Text," +
                "password Text," +
                "mail Text," +
                "danismanAd Text," +
                "danismanKurum Text," +
                "danismanMail Text," +
                "resim Text," +
                "giris Text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS bilgiler ");
    }
}
