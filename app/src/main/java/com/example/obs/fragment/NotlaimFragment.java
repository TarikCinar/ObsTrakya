package com.example.obs.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.example.obs.OnBackpressed;
import com.example.obs.R;
import com.example.obs.activity.MainActivity;
import com.example.obs.Models.DataItem;
import com.example.obs.Models.Notlarim;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NotlaimFragment extends Fragment implements OnBackpressed {

    private ExpandingList expandingList;

    private Context context;

    private TextView yazi;
    private AVLoadingIndicatorView avi;
    List<String> dersler=new ArrayList<>();
    Map<String,List<String>> notlar=new HashMap<>();
    private Handler mHandler = new Handler(Looper.getMainLooper());



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.notlarim,container,false);

        context=container.getContext();

        yazi=root.findViewById(R.id.yazi);
        avi=root.findViewById(R.id.avi);

        expandingList=root.findViewById(R.id.expandet);
        yazi.setVisibility(View.INVISIBLE);

        expandingList.setVisibility(View.INVISIBLE);


        Notlar notlar=new Notlar();
        notlar.execute();

        return root;
    }

    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getContext(), MainActivity.class));
        return false;
    }

    public class Notlar extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                SharedPreferences sharedPreferences=context.getSharedPreferences("bilgiler", Context.MODE_PRIVATE);

                String login = sharedPreferences.getString("username","0") + ":" + sharedPreferences.getString("password","0");
                final String base64login = new String(Base64.encode(login.getBytes(), Base64.NO_WRAP));

                Connection.Response document = Jsoup
                        .connect("https://obs.trakya.edu.tr/api/ogrenci")
                        .ignoreContentType(true)
                        .header("Authorization", "Basic " + base64login)
                        .execute();
                Document doc = Jsoup.connect("https://obs.trakya.edu.tr/api/ogrenciders")
                        .cookies(document.cookies())
                        .ignoreContentType(true)
                        .get();

                String veri=doc.body().html()
                        .replaceAll("null","\"-\"")
                        .replaceAll("\n<br>","\t");
                Gson gson=new Gson();
                Notlarim data=gson.fromJson(veri, Notlarim.class);
                List<DataItem> dataItem=data.getData();


                List<String> vize1=new ArrayList<>();
                List<String> vize2=new ArrayList<>();
                List<String> vize3=new ArrayList<>();
                List<String> vize4=new ArrayList<>();
                List<String> vize5=new ArrayList<>();
                List<String> vize6=new ArrayList<>();
                List<String> vize7=new ArrayList<>();
                List<String> vize8=new ArrayList<>();
                List<String> vize9=new ArrayList<>();
                List<String> vize10=new ArrayList<>();
                List<String> _final=new ArrayList<>();
                List<String> harf=new ArrayList<>();
                List<String> butunleme=new ArrayList<>();


                for(DataItem d:dataItem){
                    boolean kontrol=true;
                    for(String s:dersler){
                        if(s.equals(d.getDersAdi())){
                            kontrol=false;
                        }

                    }
                    if (kontrol){
                        dersler.add(d.getDersAdi());
                        vize1.add(d.getVize1Adi());
                        vize2.add(d.getVize2Adi());
                        vize3.add(d.getVize3Adi());
                        vize4.add(d.getVize4Adi());
                        vize5.add(d.getVize5Adi());
                        vize6.add(d.getVize6Adi());
                        vize7.add(d.getVize7Adi());
                        vize8.add(d.getVize8Adi());
                        vize9.add(d.getVize9Adi());
                        vize10.add(d.getVize10Adi());
                        _final.add(String.valueOf(d.getFinalAdi()));
                        harf.add("Harf Notu\t "+d.getHarfNotKarsilik());
                        butunleme.add("Bütünleme\t "+d.getButunlemeAdi().toString());


                    }
                }
                for (int i=0;i<dersler.size();i++){
                    List<String> tutucu=new ArrayList<>();
                    tutucu.add(vize1.get(i));
                    tutucu.add(vize2.get(i));
                    tutucu.add(vize3.get(i));
                    tutucu.add(vize4.get(i));
                    tutucu.add(vize5.get(i));
                    tutucu.add(vize6.get(i));
                    tutucu.add(vize7.get(i));
                    tutucu.add(vize8.get(i));
                    tutucu.add(vize9.get(i));
                    tutucu.add(vize10.get(i));
                    tutucu.add(_final.get(i));
                    tutucu.add(harf.get(i));
                    tutucu.add(butunleme.get(i));
                    notlar.put(dersler.get(i),tutucu);
                }


                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        avi.setVisibility(View.INVISIBLE);
                        yazi.setVisibility(View.VISIBLE);
                        expandingList.setVisibility(View.VISIBLE);
                    }
                });

                for (final String ke:notlar.keySet()){
                    Random rnd = new Random();
                    final int color = Color.argb(255, rnd.nextInt(200), rnd.nextInt(220), rnd.nextInt(256));
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            createItems(ke,notlar.get(ke),color);

                        }
                    });

                }


            } catch (IOException e) {
            }
            return null;
        }
    }


    private void createItems(String key,List<String> list,int color) {
        addItem(key,list, color, R.drawable.not_icon);
    }

    private void addItem(String title, List<String> subItems, int colorRes, int iconRes) {
        //Let's create an item with R.layout.expanding_layout
        final ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout);

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColor(colorRes);
            item.setIndicatorIconRes(iconRes);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item
            ((TextView) item.findViewById(R.id.title)).setText(title);

            //We can create items in batch.
            item.createSubItems(subItems.size());
            for (int i = 0; i < item.getSubItemsCount(); i++) {
                //Let's get the created sub item by its index
                final View view = item.getSubItemView(i);

                //Let's set some values in
                configureSubItem(item, view, subItems.get(i));
            }
        }
    }

    private void configureSubItem(final ExpandingItem item, final View view, String subTitle) {
        ((TextView) view.findViewById(R.id.sub_title)).setText(subTitle);
    }

}
