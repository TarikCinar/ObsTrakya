package com.example.obs.activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.obs.BottomNavigationBehavior;
import com.example.obs.BuildConfig;
import com.example.obs.DarkModePrefManager;
import com.example.obs.R;
import com.example.obs.fragment.DanismanFragment;
import com.example.obs.fragment.DuyuruFragment;
import com.example.obs.fragment.DuyuruGosterFragment;
import com.example.obs.fragment.HaberGosterFragment;
import com.example.obs.fragment.HaberlerFragment;
import com.example.obs.fragment.HomeFragment;
import com.example.obs.fragment.NotlaimFragment;
import com.example.obs.fragment.ProfilFragment;
import com.example.obs.fragment.TranskriptFragment;
import com.example.obs.sqlite.Bilgiler;
import com.example.obs.sqlite.BilgilerDao;
import com.example.obs.sqlite.BilgilerYardimci;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;




public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static BottomNavigationView bottomNavigationView;
    private static final int MODE_DARK = 0;
    private static final int MODE_LIGHT = 1;

    private Fragment tempFragment;
    private CircleImageView profil;
    private TextView isim,mail;
    private BilgilerYardimci yardimci;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                    tempFragment=new ProfilFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.soldan_giris, R.anim.sagdan_cikis);
                    transaction.replace(R.id.fragment_tutucu, tempFragment);
                    transaction.commit();
                    return true;
                case R.id.navigationMyCourses:
                    tempFragment=new DuyuruFragment();
                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();

                    transaction2.setCustomAnimations(R.anim.soldan_giris, R.anim.sagdan_cikis);
                    transaction2.replace(R.id.fragment_tutucu, tempFragment);
                    transaction2.commit();
                    return true;
                case R.id.navigationHome:
                    tempFragment=new HomeFragment();
                    FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();

                    transaction3.setCustomAnimations(R.anim.soldan_giris, R.anim.sagdan_cikis);
                    transaction3.replace(R.id.fragment_tutucu, tempFragment);
                    transaction3.commit();
                    return true;
                case  R.id.navigationHaberler:
                    tempFragment=new HaberlerFragment();
                    FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();

                    transaction4.setCustomAnimations(R.anim.soldan_giris, R.anim.sagdan_cikis);
                    transaction4.replace(R.id.fragment_tutucu, tempFragment);
                    transaction4.commit();
                    return true;
                case  R.id.navigationMenu:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDarkMode(getWindow());
        setContentView(R.layout.activity_main);




        SharedPreferences sharedPreferences=getSharedPreferences("bilgiler", MODE_PRIVATE);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        profil=navigationView.getHeaderView(0).findViewById(R.id.profil);
        isim=navigationView.getHeaderView(0).findViewById(R.id.isim);
        mail=navigationView.getHeaderView(0).findViewById(R.id.mail);
        isim.setText(sharedPreferences.getString("isimSoyisim",""));
        mail.setText(sharedPreferences.getString("mail",""));
        profil.setImageResource(R.drawable.danisman);

        yardimci=new BilgilerYardimci(this);

        SharedPreferences sp=getSharedPreferences("bilgiler", Context.MODE_PRIVATE);
        ArrayList<Bilgiler> bilgilers=new BilgilerDao().bilgiAl(yardimci,sp.getString("username",""));

        String resim ="";
        for (Bilgiler bil:bilgilers){
            resim=bil.getResim();
        }

        if (resim.equals("null")){
            String login = sp.getString("username","0") + ":" + sp.getString("password","0");
            final String base64login = new String(Base64.encode(login.getBytes(), Base64.NO_WRAP));
            final OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("Authorization", "Basic " + base64login)
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .build();


            Picasso picasso = new Picasso.Builder(this)
                    .downloader(new OkHttp3Downloader(client))
                    .build();
            picasso.load("https://obs.trakya.edu.tr/api/ogrenciresim").into(profil);
        }else{
            SharedPreferences myPrefrence =getSharedPreferences(sp.getString("username",""),MODE_PRIVATE);
            String imageS = myPrefrence.getString("imagePreferance", "");
            Bitmap imageB;
            if(!imageS.equals("")) {
                imageB = decodeToBase64(imageS);
                profil.setImageBitmap(imageB);
            }
        }



        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setSelectedItemId(R.id.navigationHome);



        if (getIntent().getBooleanExtra("referer",false)){
            System.out.println(getIntent().getBooleanExtra("referer",false));
            Fragment fragment = new DuyuruFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_tutucu, fragment).commit();
        }

        if (getIntent().getBooleanExtra("haber",false)){
            bottomNavigationView.setSelectedItemId(R.id.navigationHaberler);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            tellFragments();
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profil) {
            bottomNavigationView.setSelectedItemId(R.id.navigationMyProfile);
        } else if (id == R.id.geriBildirim) {

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL  , new String[] { "ersintarik89@gmail.com" });
            intent.putExtra(Intent.EXTRA_SUBJECT, "Trakya OBS Geri Bildirimi");

            startActivity(Intent.createChooser(intent, "Mail uygulamasını seçin"));


        } else if (id == R.id.anasayfa) {
            tempFragment=new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu,tempFragment).commit();

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Merhaba Trakya Üniversitesinde okuyorsan bu uygulama tam senlik: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_dark_mode) {
            //code for setting dark mode
            //true for dark mode, false for day mode, currently toggling on each click
            DarkModePrefManager darkModePrefManager = new DarkModePrefManager(this);
            darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();

        }else if(id==R.id.cikis){
            SharedPreferences sp=getSharedPreferences("bilgiler",Context.MODE_PRIVATE);
            String rawQuery=String.format("UPDATE bilgiler SET giris='%s' WHERE okulNo='%s'","false",sp.getString("username",""));
            new BilgilerDao().update(yardimci,rawQuery);
            startActivity(new Intent(this,GirisActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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



    private void tellFragments(){

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment f : fragments){
            if(f != null && f instanceof TranskriptFragment)
                ((TranskriptFragment)f).onBackPressed();
            if(f != null && f instanceof DuyuruFragment)
                ((DuyuruFragment)f).onBackPressed();
            if(f != null && f instanceof DanismanFragment)
                ((DanismanFragment)f).onBackPressed();
            if(f != null && f instanceof NotlaimFragment)
                ((NotlaimFragment)f).onBackPressed();
            if(f != null && f instanceof DuyuruGosterFragment){
                ((DuyuruGosterFragment)f).onBackPressed();
            }
            if(f != null && f instanceof HaberGosterFragment){
                ((HaberGosterFragment)f).onBackPressed();
            }

        }
    }

    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
