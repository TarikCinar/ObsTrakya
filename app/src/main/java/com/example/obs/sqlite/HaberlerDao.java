package com.example.obs.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class HaberlerDao {

    public void haberEkle(HaberlerYardimci yardimci,String baslik,String altBaslik,String link,String resim){
        SQLiteDatabase db=yardimci.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("baslik",baslik);
        content.put("altBaslik",altBaslik);
        content.put("link",link);
        content.put("resim",resim);
        db.insertOrThrow("haberler",null,content);
        db.close();
    }

    public ArrayList<Haberler> tumHaberler(HaberlerYardimci yardimci){
        ArrayList<Haberler> haberlerArrayList=new ArrayList<>();
        SQLiteDatabase db=yardimci.getReadableDatabase();

        Cursor c=db.rawQuery("SELECT * FROM haberler",null);

        while (c.moveToNext()){
            Haberler haberler=new Haberler(c.getString(c.getColumnIndex("resim")),
                    c.getString(
                    c.getColumnIndex("baslik")),
                    c.getString(c.getColumnIndex("altBaslik")),
                    c.getString(c.getColumnIndex("link")));
            haberlerArrayList.add(haberler);
        }
        return haberlerArrayList;
    }

    public void databaseDelete(HaberlerYardimci sqliteYardincisi){
        SQLiteDatabase db=sqliteYardincisi.getReadableDatabase();
        db.delete("haberler",null,null);
    }
}
