package com.example.obs.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obs.R;
import com.example.obs.adapter.DuyurularAdapter;
import com.example.obs.adapter.HaberlerAdapter;
import com.example.obs.sqlite.Duyurular;
import com.example.obs.sqlite.DuyurularDao;
import com.example.obs.sqlite.Haberler;
import com.example.obs.sqlite.HaberlerDao;
import com.example.obs.sqlite.HaberlerYardimci;
import com.example.obs.sqlite.SqliteYardimci;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class HaberlerFragment extends Fragment {

    private ArrayList<String> baslik=new ArrayList<>();
    private ArrayList<String> altBaslik=new ArrayList<>();
    private ArrayList<String> link=new ArrayList<>();
    private ArrayList<String> resim=new ArrayList<>();
    private HaberlerAdapter haberlerAdapter;
    RecyclerView haberlerRv;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    AVLoadingIndicatorView avi;
    private HaberlerYardimci yardimci;


    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.haber_fragment,container,false);

        context=container.getContext();

        yardimci=new HaberlerYardimci(context);
        ArrayList<Haberler> gelenKelimeler=new HaberlerDao().tumHaberler(yardimci);

        for (Haberler d:gelenKelimeler){
            baslik.add(d.getBaslik());
            altBaslik.add(d.getAltBaslik());
            link.add(d.getLink());
            resim.add(d.getResim());
        }
        haberlerRv = root.findViewById(R.id.haberlerRv);
        haberlerRv.setHasFixedSize(true);
        haberlerRv.setLayoutManager(new LinearLayoutManager(getContext()));
        haberlerAdapter= new HaberlerAdapter(getContext(),baslik,altBaslik,link,resim);
        haberlerRv.setAdapter(haberlerAdapter);
        return root;
    }
}
