package com.example.obs.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DuyurularDao {

    public void duyuruEkle(SqliteYardimci yardimci,String duyuru_Ad,String duyuru_link){
        SQLiteDatabase db=yardimci.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("duyuru_ad",duyuru_Ad);
        content.put("duyuru_link",duyuru_link);
        db.insertOrThrow("duyurular",null,content);
        db.close();
    }

    public ArrayList<Duyurular> tumKelimeler(SqliteYardimci yardimci){
        ArrayList<Duyurular> duyurularArrayList=new ArrayList<>();
        SQLiteDatabase db=yardimci.getReadableDatabase();

        Cursor c=db.rawQuery("SELECT * FROM duyurular",null);

        while (c.moveToNext()){
            Duyurular kelimeler=new Duyurular(c.getInt(c.getColumnIndex("duyuru_id")),
                    c.getString(c.getColumnIndex("duyuru_ad")),
                    c.getString(c.getColumnIndex("duyuru_link")));
            duyurularArrayList.add(kelimeler);
        }
        return duyurularArrayList;
    }

    public void databaseDelete(SqliteYardimci sqliteYardincisi){
        SQLiteDatabase db=sqliteYardincisi.getReadableDatabase();
        db.delete("duyurular",null,null);
    }
}
