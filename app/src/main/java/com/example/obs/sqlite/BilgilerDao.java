package com.example.obs.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class BilgilerDao {
    public void bilgiEkle(BilgilerYardimci yardimci,
                           String okulNo,
                           String isim,
                           String isimSoyisim,
                           String username,
                           String password,
                           String mail,
                           String danismanAd,
                           String danismanKurum,
                           String danismanMail,
                           String resim,
                          String giris
                           ){
        SQLiteDatabase db=yardimci.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("okulNo",okulNo);
        content.put("isim",isim);
        content.put("isimSoyisim",isimSoyisim);
        content.put("username",username);
        content.put("password",password);
        content.put("mail",mail);
        content.put("danismanAd",danismanAd);
        content.put("danismanKurum",danismanKurum);
        content.put("danismanMail",danismanMail);
        content.put("resim",resim);
        content.put("giris",giris);
        db.insertOrThrow("bilgiler",null,content);
        db.close();
    }

    public ArrayList<Bilgiler> bilgiAl(BilgilerYardimci yardimci,String okulNo){
        ArrayList<Bilgiler> bilgilerArrayList=new ArrayList<>();
        SQLiteDatabase db=yardimci.getReadableDatabase();

        String raw = String.format("SELECT * FROM bilgiler WHERE okulno='%s'", okulNo);

        Cursor c=db.rawQuery(raw,null);

        while (c.moveToNext()){
            Bilgiler bilgiler=new Bilgiler(c.getString(c.getColumnIndex("okulNo")),
                    c.getString(c.getColumnIndex("isim")),
                    c.getString(c.getColumnIndex("isimSoyisim")),
                    c.getString(c.getColumnIndex("username")),
                    c.getString(c.getColumnIndex("password")),
                    c.getString(c.getColumnIndex("mail")),
                    c.getString(c.getColumnIndex("danismanAd")),
                    c.getString(c.getColumnIndex("danismanKurum")),
                    c.getString(c.getColumnIndex("danismanMail")),
                    c.getString(c.getColumnIndex("resim")),
                    c.getString(c.getColumnIndex("giris"))
            );
            bilgilerArrayList.add(bilgiler);
        }
        return bilgilerArrayList;
    }

    public void databaseDelete(BilgilerYardimci sqliteYardincisi){
        SQLiteDatabase db=sqliteYardincisi.getReadableDatabase();
        db.delete("bilgiler",null,null);
    }

    public void update(BilgilerYardimci yardimci,String rawQuery){
        SQLiteDatabase db=yardimci.getWritableDatabase();
        db.execSQL(rawQuery);
    }
}
