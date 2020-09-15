package com.example.obs.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.obs.DarkModePrefManager;
import com.example.obs.R;
import com.example.obs.Models.Response;
import com.example.obs.sqlite.Bilgiler;
import com.example.obs.sqlite.BilgilerDao;
import com.example.obs.sqlite.BilgilerYardimci;
import com.example.obs.sqlite.DuyurularDao;
import com.example.obs.sqlite.HaberlerDao;
import com.example.obs.sqlite.HaberlerYardimci;
import com.example.obs.sqlite.SqliteYardimci;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;

public class GirisActivity extends AppCompatActivity {
    private TextView hataMesaji1,hataMesaji2,hataliGiris;
    private Button buttonYenile;
    private EditText okulNo;
    private EditText parola;
    private Button login;
    String username;
    String password;
    private CardView loginCard;
    private CheckBox checkBox;
    private AVLoadingIndicatorView avi;
    private static final int MODE_DARK = 0;
    private static final int MODE_LIGHT = 1;
    private SqliteYardimci sq;
    private HaberlerYardimci haberlerYardimci;
    private BilgilerYardimci bilgilerYardimci;
    String isimSoyisim;
    String isim;
    String mail;
    String danismanAd;
    String danismanMail;
    String danismanKurum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDarkMode(getWindow());

        setContentView(R.layout.activity_giris);
        checkBox=findViewById(R.id.checkBox);

        sq=new SqliteYardimci(this);
        bilgilerYardimci=new BilgilerYardimci(this);
        haberlerYardimci=new HaberlerYardimci(this);



        new DuyurularDao().databaseDelete(sq);
        new HaberlerDao().databaseDelete(haberlerYardimci);



        hataMesaji1=findViewById(R.id.hataMesaji1);
        hataMesaji2=findViewById(R.id.hataMesaji2);
        buttonYenile=findViewById(R.id.buttonYenile);
        buttonYenile.setVisibility(View.INVISIBLE);

        hataMesaji1.setVisibility(View.INVISIBLE);
        hataMesaji2.setVisibility(View.INVISIBLE);
        hataliGiris=findViewById(R.id.hataliGiris);

        okulNo=findViewById(R.id.okulNo);
        parola=findViewById(R.id.parola);
        login=findViewById(R.id.login);
        loginCard=findViewById(R.id.loginCard);


        avi=findViewById(R.id.avi);
        avi.setVisibility(View.INVISIBLE);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressLint("MissingPermission")
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();


        if (isConnected) {

            Duyuru duyuru=new Duyuru();
            duyuru.execute();


            SharedPreferences sp=getSharedPreferences("bilgiler",Context.MODE_PRIVATE);
            final ArrayList<Bilgiler> bilgilers=new BilgilerDao().bilgiAl(bilgilerYardimci,sp.getString("username",""));
            String giris ="";
            for (Bilgiler bil:bilgilers){
                giris=bil.getGiris();
            }
            if (giris.equals("true")){
                username= String.valueOf(sp.getString("username",""));
                password= String.valueOf(sp.getString("password",""));
                avi.setVisibility(View.VISIBLE);
                loginCard.setVisibility(View.INVISIBLE);
                Basic basic=new Basic();
                basic.execute();
            }

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    username= String.valueOf(okulNo.getText());
                    password= String.valueOf(parola.getText());
                    avi.setVisibility(View.VISIBLE);
                    loginCard.setVisibility(View.INVISIBLE);

                    Basic basic=new Basic();
                    basic.execute();

                    if (checkBox.isChecked()){
                        ArrayList<Bilgiler> bilgiler=new BilgilerDao().bilgiAl(bilgilerYardimci,username);
                        if (bilgiler.size()==0){
                            new BilgilerDao().bilgiEkle(bilgilerYardimci,
                                    username,
                                    isim,
                                    isimSoyisim,
                                    username,
                                    password,
                                    mail,
                                    danismanAd,
                                    danismanKurum,
                                    danismanMail,
                                    "null",
                                    "true"
                            );
                        }else {
                            String rawQuery=String.format("UPDATE bilgiler SET username='%s'," +
                                    " giris='%s'," +
                                    " password='%s'" +
                                    " WHERE okulNo='%s'",username,"true",password,username);
                            new BilgilerDao().update(bilgilerYardimci,rawQuery);
                        }

                    }else {
                        ArrayList<Bilgiler> bilgiler=new BilgilerDao().bilgiAl(bilgilerYardimci,username);
                        if (bilgiler.size()==0){
                            new BilgilerDao().bilgiEkle(bilgilerYardimci,
                                    username,
                                    isim,
                                    isimSoyisim,
                                    username,
                                    password,
                                    mail,
                                    danismanAd,
                                    danismanKurum,
                                    danismanMail,
                                    "null",
                                    "false"
                            );
                        }
                        else{
                            String rawQuery=String.format("UPDATE bilgiler SET username='%s'," +
                                    " giris='%s'," +
                                    " password='%s'" +
                                    " WHERE okulNo='%s'",username,"false",password,username);
                            new BilgilerDao().update(bilgilerYardimci,rawQuery);
                        }
                    }
                }
            });

        } else {
            hataMesaji1.setVisibility(View.VISIBLE);
            hataMesaji2.setVisibility(View.VISIBLE);
            buttonYenile.setVisibility(View.VISIBLE);
            loginCard.setVisibility(View.INVISIBLE);
        }
        buttonYenile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerRebirth(getApplicationContext());
            }
        });
    }

    public static void triggerRebirth(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public class Duyuru extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            new HaberlerDao().databaseDelete(haberlerYardimci);

            Document duyuru = null;
                try {
                    duyuru = Jsoup.connect("https://www.trakya.edu.tr/news_cats/duyurular").get();

                    Elements elements=duyuru.getElementsByClass("item text-theme-colored");
                    for (Element baslik :elements) {
                        Elements link = baslik.select("a[href]");
                        new DuyurularDao().duyuruEkle(sq,baslik.text(),link.attr("abs:href"));
                    }

                    SharedPreferences sharedPreferences=getSharedPreferences("duyurular",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("duyurular",elements.html());
                    editor.apply();

                    Document haberler = Jsoup.connect("https://www.trakya.edu.tr/news_cats/haberler").get();
                    Elements haber=haberler.getElementsByClass("col-sm-6 col-md-4");
                    Elements habericerik=haberler.getElementsByClass("entry-title text-white text-uppercase font-weight-600 m-0 mt-5 overflow-hidden");


                    SharedPreferences sp=getSharedPreferences("haberler",MODE_PRIVATE);
                    SharedPreferences.Editor spedit=sp.edit();
                    spedit.putString("haberler",habericerik.text());
                    spedit.apply();

                    for (Element h:haber){
                        Elements link = h.select("a[href]");
                        String haberLink=link.attr("abs:href");
                        String baslik=h.select("h4").text();
                        String altBaslik=h.select("p").text();
                        Element resim=h.select("img").first();
                        String resimUrl=resim.absUrl("src");
                        new HaberlerDao().haberEkle(haberlerYardimci,baslik,altBaslik,haberLink,resimUrl);

                    }

                    Document haberler2=Jsoup.connect("https://www.trakya.edu.tr/news_cats/haberler/2").get();
                    Elements haber2=haberler2.getElementsByClass("col-sm-6 col-md-4");
                    for (Element h:haber2){
                        Elements link = h.select("a[href]");
                        String haberLink=link.attr("abs:href");
                        String baslik=h.select("h4").text();
                        String altBaslik=h.select("p").text();
                        Element resim=h.select("img").first();
                        String resimUrl=resim.absUrl("src");
                        new HaberlerDao().haberEkle(haberlerYardimci,baslik,altBaslik,haberLink,resimUrl);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            return null;
        }
    }
    public class Basic extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                final String login = username + ":" + password;
                String base64login = new String(Base64.encode(login.getBytes(), Base64.NO_WRAP));

                Connection.Response document = Jsoup
                        .connect("https://obs.trakya.edu.tr/api/ogrenci")
                        .ignoreContentType(true)
                        .header("Authorization", "Basic " + base64login)
                        .execute();

                Document doc = Jsoup.connect("https://obs.trakya.edu.tr/api/ogrenci")
                        .cookies(document.cookies())
                        .ignoreContentType(true)
                        .get();

                Document yemek = Jsoup.connect("https://www.trakya.edu.tr/yemek-listesi/")
                        .cookies(document.cookies())
                        .ignoreContentType(true)
                        .get();

                Document ders = Jsoup.connect("https://obs.trakya.edu.tr/api/ogrenciders")
                        .cookies(document.cookies())
                        .ignoreContentType(true)
                        .get();

                String veri=ders.body().html();


                SharedPreferences sharedPreferencesDers=getSharedPreferences("ders",MODE_PRIVATE);
                SharedPreferences.Editor seditor=sharedPreferencesDers.edit();
                seditor.putString("ders",veri);
                seditor.apply();

                Elements elements=yemek.getElementsByClass("alert alert-success xs-text-center");
                String yemekList = "";
                for (Element e:elements){
                    Elements y=e.getElementsByClass("text-nowrap");
                    for (Element s:y){
                        yemekList+=s.text()+"\n";
                    }
                }
                Log.e("yemelk",yemekList);

                Gson gson=new Gson();
                final Response data=gson.fromJson(doc.body().text(),Response.class);
                 isimSoyisim=data.getData().getAdiSoyadi();
                 isim=data.getData().getAdi();
                 mail=data.getData().getEmail();
                 danismanAd=data.getData().getDanisman().getUnvaniAdiSoyadi();
                 danismanKurum=data.getData().getDanisman().getCalistigiBirimAdi();
                 danismanMail=data.getData().getDanisman().getEmail();




                SharedPreferences sharedPreferences=getSharedPreferences("bilgiler",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                if (checkBox.isChecked()){
                    editor.putBoolean("giris",true);
                }else {
                    editor.putBoolean("giris",false);
                }
                editor.putString("username",username);
                editor.putString("password",password);
                editor.putString("isim",isim);
                editor.putString("isimSoyisim",isimSoyisim);
                editor.putString("mail",mail);
                editor.putString("danismanAd",data.getData().getDanisman().getUnvaniAdiSoyadi());
                editor.putString("danismanKurum",data.getData().getDanisman().getCalistigiBirimAdi());
                editor.putString("danismanMail",data.getData().getDanisman().getEmail());
                editor.putBoolean("resim",false);
                editor.putString("yemek",yemekList);
                editor.apply();

                String paht=String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));


                Intent intent=new Intent(GirisActivity.this,MainActivity.class);
                startActivity(intent);

                finish();


            } catch (IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        avi.setVisibility(View.INVISIBLE);
                        loginCard.setVisibility(View.VISIBLE);
                        findViewById(R.id.hataliGiris).setVisibility(View.VISIBLE);
                    }
                });
            }
            return null;
        }
    }

    //create a seperate class file, if required in multiple activities
    public void setDarkMode(Window window){
        if(new DarkModePrefManager(this).isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            changeStatusBar(MODE_DARK,window);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            changeStatusBar(MODE_LIGHT,window);
        }
    }
    public void changeStatusBar(int mode, Window window){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.contentStatusBar));
            //Light mode
            if(mode==MODE_LIGHT){
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }
}
