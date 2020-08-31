package com.example.obs.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.obs.R;
import com.example.obs.activity.MainActivity;
import com.example.obs.adapter.BilgilerAdapter;
import com.example.obs.adapter.DuyurularAdapter;
import com.example.obs.sqlite.Bilgiler;
import com.example.obs.sqlite.BilgilerDao;
import com.example.obs.sqlite.BilgilerYardimci;
import com.example.obs.sqlite.Duyurular;
import com.example.obs.sqlite.DuyurularDao;
import com.example.obs.sqlite.SqliteYardimci;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HomeFragment extends Fragment {

    private int[] bilgilerList=new int[3];
    private String[] bilgilerListAd=new String[3];
    private BilgilerAdapter bilgilerAdapter;
    public String cookie;
    private TextView textViewKullaniciAdi;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ArrayList<String> duyurularList=new ArrayList<>();
    private ArrayList<String> duyurularLink=new ArrayList<>();
    private DuyurularAdapter duyurularAdapter;
    RecyclerView duyurularRv;
    private TextView tumunuGor;
    private CircleImageView profilImage;
    Context context;
    private TextView yemek;
    private SqliteYardimci sq;
    private BilgilerYardimci yardimci;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.content_main,container,false);
        context=container.getContext();
        profilImage=root.findViewById(R.id.profilImage);

        yemek=root.findViewById(R.id.yemek);

        sq=new SqliteYardimci(context);
        yardimci=new BilgilerYardimci(context);

        SharedPreferences sp=context.getSharedPreferences("bilgiler",Context.MODE_PRIVATE);
        ArrayList<Bilgiler> bilgilers=new BilgilerDao().bilgiAl(yardimci,sp.getString("username",""));

        yemek.setText(sp.getString("yemek",""));

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

            Picasso picasso = new Picasso.Builder(context)
                    .downloader(new OkHttp3Downloader(client))
                    .build();
            picasso.load("https://obs.trakya.edu.tr/api/ogrenciresim").into(profilImage);
        }else{
            SharedPreferences myPrefrence = getActivity().getSharedPreferences(sp.getString("username",""),context.MODE_PRIVATE);
            String imageS = myPrefrence.getString("imagePreferance", "");
            Bitmap imageB;
            if(!imageS.equals("")) {
                imageB = decodeToBase64(imageS);
                profilImage.setImageBitmap(imageB);
            }
        }


        ArrayList<Duyurular> gelenKelimeler=new DuyurularDao().tumKelimeler(sq);

        int i=0;
        for (Duyurular d:gelenKelimeler){
            if (i==4){
                break;
            }
            duyurularList.add(d.getDuyuru_ad());
            duyurularLink.add(d.getDuyuru_link());
            i++;
        }



        textViewKullaniciAdi=root.findViewById(R.id.textViewKullaniciAdi);
        textViewKullaniciAdi.setText("Merhaba "+sp.getString("isim",""));

        tumunuGor=root.findViewById(R.id.tumunuGor);
        tumunuGor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.bottomNavigationView.setSelectedItemId(R.id.navigationMyCourses);
            }
        });


        RecyclerView bilgilerRv = root.findViewById(R.id.bil);
        duyurularRv = root.findViewById(R.id.duyurularRv);

        bilgilerRv.setHasFixedSize(true);
        bilgilerRv.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        bilgilerList[0]=R.drawable.note;
        bilgilerList[1]=R.drawable.danisman;
        bilgilerList[2]=R.drawable.transkript;
        bilgilerListAd[0]="Notlarım";
        bilgilerListAd[1]="Danışmanım";
        bilgilerListAd[2]="Transkript";
        bilgilerAdapter=new BilgilerAdapter(getContext(),bilgilerList,bilgilerListAd);
        bilgilerRv.setAdapter(bilgilerAdapter);
        duyurularRv.setHasFixedSize(true);
        duyurularRv.setLayoutManager(new LinearLayoutManager(getContext()));
        duyurularAdapter=new DuyurularAdapter(getContext(),duyurularList,duyurularLink,false);
        duyurularRv.setAdapter(duyurularAdapter);
        return root;
    }


    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

}
