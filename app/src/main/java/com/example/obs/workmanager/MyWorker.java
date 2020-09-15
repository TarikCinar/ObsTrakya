package com.example.obs.workmanager;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.obs.R;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @NonNull
    @Override
    public Result doWork() {

        VeriAl veriAl=new VeriAl();
        veriAl.execute();

        return Result.success();
    }

    class VeriAl extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.e("ask task ","çalıştı");
                SharedPreferences bilgiler=getApplicationContext().getSharedPreferences("bilgiler",Context.MODE_PRIVATE);
                String username= String.valueOf(bilgiler.getString("username",""));
                String password= String.valueOf(bilgiler.getString("password",""));
                final String login = username + ":" + password;
                String base64login = new String(Base64.encode(login.getBytes(), Base64.NO_WRAP));

                Connection.Response document = Jsoup
                        .connect("https://obs.trakya.edu.tr/api/ogrenci")
                        .ignoreContentType(true)
                        .header("Authorization", "Basic " + base64login)
                        .execute();

                Document ders = Jsoup.connect("https://obs.trakya.edu.tr/api/ogrenciders")
                        .cookies(document.cookies())
                        .ignoreContentType(true)
                        .get();

                String veri=ders.body().html();

                SharedPreferences sharedPreferencesDers=getApplicationContext().getSharedPreferences("ders",Context.MODE_PRIVATE);

                if (!veri.equals(sharedPreferencesDers.getString("ders", ""))){
                    notifocion(bilgiler.getString("isim",null),"Yeni bir not girildi.");
                    SharedPreferences.Editor seditor=sharedPreferencesDers.edit();
                    seditor.putString("ders",veri);
                    seditor.apply();
                }





                Document haberler = Jsoup.connect("https://www.trakya.edu.tr/news_cats/haberler").get();
                Elements haber=haberler.getElementsByClass("entry-title text-white text-uppercase font-weight-600 m-0 mt-5 overflow-hidden");
                SharedPreferences sp=getApplicationContext().getSharedPreferences("haberler",Context.MODE_PRIVATE);


                if (!haber.text().equals(sp.getString("haberler", ""))){
                    notifocion(bilgiler.getString("isim",null),"Trakya üniversitesi yeni bir haber yayınladı.");
                    SharedPreferences.Editor spedit=sp.edit();
                    spedit.putString("haberler",haber.text());
                    spedit.apply();
                }

                Document duyuru = Jsoup.connect("https://www.trakya.edu.tr/news_cats/duyurular").get();

                Elements elements=duyuru.getElementsByClass("item text-theme-colored");
                SharedPreferences sharedPreferencesDuyurular=getApplicationContext().getSharedPreferences("duyurular",Context.MODE_PRIVATE);
                if (!elements.html().equals(sharedPreferencesDuyurular.getString("duyurular", ""))){

                    notifocion(bilgiler.getString("isim",null),"Trakya üniversitesi yeni bir duyuru yayınladı.");

                    SharedPreferences.Editor editor=sharedPreferencesDuyurular.edit();
                    editor.putString("duyurular",elements.html());
                    editor.apply();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public void notifocion(String title,String icerik){
        Log.e("icerik",icerik);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("simplifiedcoding", "simplifiedcoding", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "simplifiedcoding")
                .setContentTitle("Hey! "+title)
                .setContentText(icerik)
                .setSmallIcon(R.mipmap.obs_icon);
        notificationManager.notify(1, notification.build());
    }
}

